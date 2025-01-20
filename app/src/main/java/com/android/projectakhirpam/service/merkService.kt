package com.android.projectakhirpam.service

import com.android.projectakhirpam.model.AllMerkResponse
import com.android.projectakhirpam.model.Merk
import com.android.projectakhirpam.model.MerkDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface merkService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("store")
    suspend fun insertMerk(@Body merk: Merk)

    @GET(".")
    suspend fun getAllMerk(): AllMerkResponse

    @GET("{id}")
    suspend fun getMerkById(@Path("id") idMerk: Int): MerkDetailResponse

    @PUT("{id}")
    suspend fun updateMerk(@Path("id") idMerk: Int, @Body merk: Merk)

    @DELETE("{id}")
    suspend fun deleteMerk(@Path("id") idMerk: Int): Response<Void>
}
