package com.example.swiftcartapp.viewmodel.product

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftcartapp.data.repository.AuthRepo
import com.example.swiftcartapp.model.product.AddProductRequest
import com.example.swiftcartapp.model.product.ProductResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    val productRepo: AuthRepo
): ViewModel(){
    private val _productResponse = MutableStateFlow<List<ProductResponse>?>(null)
    val productResponse = _productResponse

    private val _uploadProductResponse = MutableStateFlow<String?>(null)
    val uploadProductResponse = _uploadProductResponse

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    var imageUrl: String? by mutableStateOf(null)
    var uploadError: String? by mutableStateOf(null)
    var productAdded: Boolean by mutableStateOf(false)
    var isUploading: Boolean by mutableStateOf(false)


    fun getProducts(){
        viewModelScope.launch {
            try {
                _isError.value = false
                val res = productRepo.getAllProduct()
                if (res.isSuccessful){
                    _productResponse.value = res.body()
                }else{
                    _isError.value = true
                }
            }catch (e: Exception){
                e.printStackTrace()
                _isError.value = true
            }
        }
    }

    suspend fun uploadProductImage(context: Context, uri: Uri, token: String): String? = withContext(Dispatchers.IO) {
        try {
            // Convert Uri to File
            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
            val outputStream = FileOutputStream(tempFile)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()

            // Prepare multipart
            val requestFile = tempFile.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", tempFile.name, requestFile)

            // Call repo
            val response = productRepo.uploadImage(token, body)
            return@withContext if (response.isSuccessful) {
                uploadError = null
                response.body()?.url
            } else {
                if(response.code() == 413){
                    uploadError = "Image size too large"
                }else if(response.code() == 503){
                    uploadError = "Service currently unavailable"
                }else{
                    uploadError = "Upload failed: ${response.code()}"
                }
                null
            }
        } catch (e: Exception) {
            uploadError = "Upload error: ${e.localizedMessage}"
            null
        }
    }

    fun submitProduct(
        context: Context,
        name: String,
        price: Double,
        quantity: Int,
        imageUri: Uri,
        seller: String,
        token: String
    ){
        viewModelScope.launch {
            isUploading = true
            uploadError = null
            productAdded = false

            try {
                val uploadImageUrl = uploadProductImage(context, imageUri, token)
                if(uploadImageUrl == null){
                    return@launch
                }

                val request = AddProductRequest(
                    name = name,
                    price = price,
                    quantity = quantity,
                    imageUrl = uploadImageUrl,
                    seller = seller
                )
                println("request body: \n$request")
                val response = productRepo.addProduct(token, request)
                if(response.isSuccessful){
                    _uploadProductResponse.value = response.body().toString()
                    _isError.value = false
                    productAdded = true
                }else{
                    _isError.value = true
                    uploadError = "Product upload failed: ${response.code()}"
                }
            } catch (e: Exception) {
                _isError.value = true
                uploadError = "Error: ${e.localizedMessage}"
            } finally {
                isUploading = false
                println("token from submit product: $token")
            }
        }
    }
}