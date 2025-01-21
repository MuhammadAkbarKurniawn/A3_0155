package com.android.projectakhirpam.service

import com.android.projectakhirpam.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface pemasokService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("pemasok/store")
    suspend fun insertPemasok(@Body pemasok: Pemasok)

    @GET("pemasok")
    suspend fun getAllPemasok(): AllPemasokResponse

    @GET("pemasok/{id}")
    suspend fun getPemasokById(@Path("id") idPemasok: String): PemasokDetailResponse

    @PUT("pemasok/{id}")
    suspend fun updatePemasok(@Path("id") idPemasok: String, @Body pemasok: Pemasok)

    @DELETE("pemasok/{id}")
    suspend fun deletePemasok(@Path("id") idPemasok: String): Response<Void>
}
