package com.android.projectakhirpam.repository


import com.android.projectakhirpam.model.*
import com.android.projectakhirpam.service.kategoriService
import java.io.IOException

interface KategoriRepository {

    suspend fun getAllKategori(): AllKategoriResponse

    suspend fun insertKategori(kategori: Kategori)

    suspend fun updateKategori(idKategori: String, kategori: Kategori)

    suspend fun deleteKategori(idKategori: String)

    suspend fun getKategoriById(idKategori: String): Kategori
}

class NetworkKategoriRepository(
    private val kategoriApiService: kategoriService
) : KategoriRepository {

    override suspend fun getAllKategori(): AllKategoriResponse =
        kategoriApiService.getAllKategori()

    override suspend fun insertKategori(kategori: Kategori) {
        kategoriApiService.insertKategori(kategori)
    }

    override suspend fun updateKategori(idKategori: String, kategori: Kategori) {
        kategoriApiService.updateKategori(idKategori, kategori)
    }

    override suspend fun deleteKategori(idKategori: String) {
        try {
            val response = kategoriApiService.deleteKategori(idKategori)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete kategori. HTTP Status code: ${response.code()}"
                )
            } else {
                println("Kategori deleted successfully: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Error deleting kategori: ${e.message}")
            throw e
        }
    }

    override suspend fun getKategoriById(idKategori: String): Kategori {
        return try {
            kategoriApiService.getKategoriById(idKategori).data
        } catch (e: Exception) {
            println("Error fetching kategori by ID: ${e.message}")
            throw e
        }
    }
}
