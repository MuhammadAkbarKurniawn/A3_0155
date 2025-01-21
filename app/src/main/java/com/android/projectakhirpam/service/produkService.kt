package com.android.projectakhirpam.service

import com.android.projectakhirpam.model.AllProdukResponse
import com.android.projectakhirpam.model.Produk
import com.android.projectakhirpam.model.ProdukDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface produkService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // @POST("insertproduk.php")
    @POST("produk/store")
    suspend fun insertProduk(@Body produk: Produk)

    // @GET("bacaproduk.php")
    @GET("produk")
    suspend fun getAllProduk(): AllProdukResponse

    // @GET("baca1produk.php")
    @GET("produk/{id}")
    suspend fun getProdukById(@Path("id") idProduk: String): ProdukDetailResponse

    // @PUT("editproduk.php")
    @PUT("produk/{id}")
    suspend fun updateProduk(@Path("id") idProduk: String, @Body produk: Produk)

    // @DELETE("deleteproduk.php")
    @DELETE("produk/{id}")
    suspend fun deleteProduk(@Path("id") idProduk: String): Response<Void>
}
