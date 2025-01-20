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

    @POST("store")
    suspend fun insertKategori(@Body kategori: Kategori)

    @GET(".")
    suspend fun getAllKategori(): AllKategoriResponse

    @GET("{id}")
    suspend fun getKategoriById(@Path("id") idKategori: Int): KategoriDetailResponse

    @PUT("{id}")
    suspend fun updateKategori(@Path("id") idKategori: Int, @Body kategori: Kategori)

    @DELETE("{id}")
    suspend fun deleteKategori(@Path("id") idKategori: Int): Response<Void>
}
