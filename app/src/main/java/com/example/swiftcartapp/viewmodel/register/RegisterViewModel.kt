package com.example.swiftcartapp.viewmodel.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftcartapp.DI.DataStoreManager
import com.example.swiftcartapp.data.repository.AuthRepo
import com.example.swiftcartapp.model.register.RegisterRequest
import com.example.swiftcartapp.model.register.RegisterResponse
import com.example.swiftcartapp.model.user.EmailVerifyRequest
import com.example.swiftcartapp.model.user.EmailVerifyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: AuthRepo,
    private val dataStoreManager: DataStoreManager
) : ViewModel(){
    private val _registerResponse = MutableStateFlow<RegisterResponse?>(null)
    val registerResponse:StateFlow<RegisterResponse?> = _registerResponse

    private val _uiState = MutableStateFlow<String?>(null)
    val uiState: StateFlow<String?> = _uiState

    private val _verificationResponse = MutableStateFlow<EmailVerifyResponse?>(null)
    val verificationResponse:StateFlow<EmailVerifyResponse?> = _verificationResponse

    private val _isVerified = MutableStateFlow<Boolean?>(null)
    val isVerified:StateFlow<Boolean?> = _isVerified

    private val _email = MutableStateFlow<String>("")
    val email: StateFlow<String> = _email

    init {
        viewModelScope.launch {
            dataStoreManager.email.collect { storedEmail ->
                storedEmail?.let { _email.value = it }
            }
        }
    }

    fun registerUser(request: RegisterRequest){
        viewModelScope.launch {
            try {
                val response = repo.register(request)
                if(response.isSuccessful){
                    _uiState.value = "SignUp Successful ✅"
                }else{
                    _uiState.value = "Email already in use ❌"
                }
//                _registerResponse.value = response
            }catch (e:Exception){
                _uiState.value = "Exception occurred: ${e.localizedMessage}"
            }
        }
    }

    fun verifyOtp(request: EmailVerifyRequest){
        viewModelScope.launch {
            _isVerified.value = false
            try {
                val res = repo.verifyOtp(request)
                if(res.isSuccessful){
                    _isVerified.value = true
                }else{
                    _isVerified.value = false
                }
                _verificationResponse.value = res.body()
            }catch (e: Exception){
                _isVerified.value = false
            }
        }
    }

    fun saveEmail(email: String){
        viewModelScope.launch {
            dataStoreManager.saveEmail(email)
        }
    }

    fun getEmail(): Flow<String?> {
        return dataStoreManager.email
    }
}