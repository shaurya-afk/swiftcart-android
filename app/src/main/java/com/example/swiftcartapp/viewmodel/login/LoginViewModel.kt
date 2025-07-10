package com.example.swiftcartapp.viewmodel.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftcartapp.DI.DataStoreManager
import com.example.swiftcartapp.data.api.AuthApi
import com.example.swiftcartapp.data.repository.AuthRepo
import com.example.swiftcartapp.model.login.LoginRequest
import com.example.swiftcartapp.model.login.LoginResponse
import com.example.swiftcartapp.model.user.UserDataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo:AuthRepo,
    private val dataStoreManager: DataStoreManager
) :ViewModel(){
    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse: StateFlow<LoginResponse?> = _loginResponse

    private val _isTokenValid = mutableStateOf<Boolean?>(null)
    val isTokenValid: State<Boolean?> = _isTokenValid

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _user_data = MutableStateFlow<UserDataResponse?>(null)
    val user_data: StateFlow<UserDataResponse?> = _user_data

    fun loginUser(request: LoginRequest){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repo.login(request)
                _loginResponse.value = response

                dataStoreManager.saveToken(response.accessToken)
                dataStoreManager.saveEmail(request.email)
                println("The saved access token is: ${response.accessToken}\nEmail: ${request.email}")
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun isAuthenticated(){
        viewModelScope.launch {
            val response = repo.verifyAccessToken();
            if(response.isSuccessful){
                _isTokenValid.value = true
            }else{
                _isTokenValid.value = false
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            dataStoreManager.clearToken()
            _loginResponse.value = null
            _isTokenValid.value = null
        }
    }

    fun getSavedToken(): Flow<String?> {
        return dataStoreManager.accessToken
    }

    fun getEmail(): Flow<String?> {
        return dataStoreManager.email
    }

    fun getUser(email: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val token = dataStoreManager.accessToken.first()
                if(token != null){
                    val response = repo.getUser(token, email)
                    if(response.isSuccessful && response.body() != null){
                        _user_data.value = response.body()!!.data
                        println("user data:\n${response.body()}")
                    }else{
                        _user_data.value = null
                        println("user data:\n${response.body()}")
                    }
                }else{
                    _user_data.value = null
                    println("token is null!")
                }
            }catch (e: Exception) {
                e.printStackTrace()
                _user_data.value = null
                println("Error: \n${e.message}")
            }finally {
                _isLoading.value = false

            }
        }
    }
}