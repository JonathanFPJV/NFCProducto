package com.example.nfcproducto

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductoApiService {
    @GET("productos")
    suspend fun selectProductos(): List<ProductoModel>

    @GET("producto/{id}")
    suspend fun selectProducto(@Path("id") id: String): Response<ProductoModel>

    @Headers("Content-Type: application/json")
    @POST("productos")
    suspend fun insertProducto(@Body producto: ProductoModel): Response<ProductoModel>

    @PUT("productos/{id}")
    suspend fun updateProducto(@Path("id") id: String, @Body producto: ProductoModel): Response<ProductoModel>

    @DELETE("productos/{id}")
    suspend fun deleteProducto(@Path("id") id: String): Response<ProductoModel>
}