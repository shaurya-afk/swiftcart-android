package com.example.swiftcartapp.data.repository

import com.example.swiftcartapp.data.api.AuthApi
import com.example.swiftcartapp.model.images.ImageUploadResponse
import com.example.swiftcartapp.model.login.LoginRequest
import com.example.swiftcartapp.model.login.LoginResponse
import com.example.swiftcartapp.model.product.AddProductRequest
import com.example.swiftcartapp.model.product.AddProductResponse
import com.example.swiftcartapp.model.product.ProductResponse
import com.example.swiftcartapp.model.register.RegisterRequest
import com.example.swiftcartapp.model.register.RegisterResponse
import com.example.swiftcartapp.model.user.EmailVerifyRequest
import com.example.swiftcartapp.model.user.EmailVerifyResponse
import com.example.swiftcartapp.model.user.UserApiResponse
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val api: AuthApi,
) {
    suspend fun login(request: LoginRequest): LoginResponse {
        return api.login(request)
    }

    suspend fun register(request: RegisterRequest): Response<RegisterResponse> {
        return api.register(request)
    }

    suspend fun getAllProduct(): Response<List<ProductResponse>>{
        return api.getAllProducts()
    }

    suspend fun verifyAccessToken(): Response<Unit>{
        return api.verifyAccessToken()
    }

    suspend fun getUser(token: String, email: String): Response<UserApiResponse>{
        return api.getUser("Bearer $token",email)
    }

    suspend fun uploadImage(token: String, image: MultipartBody.Part): Response<ImageUploadResponse>{
        return api.uploadImage("Bearer $token", image)
    }

    suspend fun addProduct(token: String, product: AddProductRequest):Response<AddProductResponse>{
        return api.addProduct("Bearer $token",product)
    }

    suspend fun verifyOtp(req: EmailVerifyRequest): Response<EmailVerifyResponse>{
        return api.verifyOtp(req)
    }
}