package com.android.projectakhirpam.repository

import com.android.projectakhirpam.model.AllProdukResponse
import com.android.projectakhirpam.model.Produk
import com.android.projectakhirpam.service.produkService
import java.io.IOException

interface ProdukRepository {

    suspend fun getAllProduk(): AllProdukResponse

    suspend fun insertProduk(produk: Produk)

    suspend fun updateProduk(idProduk: String, produk: Produk)

    suspend fun deleteProduk(idProduk: String)

    suspend fun getProdukById(idProduk: String): Produk
}

class NetworkProdukRepository(
    private val produkApiService: produkService
) : ProdukRepository {

    override suspend fun getAllProduk(): AllProdukResponse =
        produkApiService.getAllProduk()

    override suspend fun insertProduk(produk: Produk) {
        produkApiService.insertProduk(produk)
    }

    override suspend fun updateProduk(idProduk: String, produk: Produk) {
        produkApiService.updateProduk(idProduk, produk)
    }

    override suspend fun deleteProduk(idProduk: String) {
        try {
            val response = produkApiService.deleteProduk(idProduk)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete produk. HTTP Status code: ${response.code()}"
                )
            } else {
                println("Produk deleted successfully: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Error deleting produk: ${e.message}")
            throw e
        }
    }

    override suspend fun getProdukById(idProduk: String): Produk {
        return try {
            produkApiService.getProdukById(idProduk).data
        } catch (e: Exception) {
            println("Error fetching produk by ID: ${e.message}")
            throw e
        }
    }
}
