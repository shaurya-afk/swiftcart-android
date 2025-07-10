package com.example.swiftcartapp.data.api

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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface AuthApi {
    @POST("/auth/signin")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/auth/signup")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @GET("/auth/verify")
    suspend fun verifyAccessToken(): Response<Unit>

    @GET("/public/products/getall")
    suspend fun getAllProducts(): Response<List<ProductResponse>>

    @GET("/auth/get_user/{email}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("email") email: String
    ): Response<UserApiResponse>

    @POST("/auth/verify_otp")
    suspend fun verifyOtp(
        @Body request: EmailVerifyRequest
    ): Response<EmailVerifyResponse>

    @Multipart
    @POST("/products/upload")
    suspend fun uploadImage(@Header("Authorization") token: String, @Part image: MultipartBody.Part):Response<ImageUploadResponse>

    @POST("/products/add")
    suspend fun addProduct(
        @Header("Authorization") token: String,
        @Body request: AddProductRequest
    ): Response<AddProductResponse>
}

