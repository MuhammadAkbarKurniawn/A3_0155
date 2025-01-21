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

interface kategoriService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("kategori/store")
    suspend fun insertKategori(@Body kategori: Kategori)

    @GET("kategori")
    suspend fun getAllKategori(): AllKategoriResponse

    @GET("kategori/{id}")
    suspend fun getKategoriById(@Path("id") idKategori: String): KategoriDetailResponse

    @PUT("kategori/{id}")
    suspend fun updateKategori(@Path("id") idKategori: String, @Body kategori: Kategori)

    @DELETE("kategori/{id}")
    suspend fun deleteKategori(@Path("id") idKategori: String): Response<Void>
}
