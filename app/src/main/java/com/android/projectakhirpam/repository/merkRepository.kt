package com.android.projectakhirpam.repository

import com.android.projectakhirpam.model.*
import com.android.projectakhirpam.service.merkService
import java.io.IOException

interface MerkRepository {

    suspend fun getAllMerk(): AllMerkResponse

    suspend fun insertMerk(merk: Merk)

    suspend fun updateMerk(idMerk: Int, merk: Merk)

    suspend fun deleteMerk(idMerk: Int)

    suspend fun getMerkById(idMerk: Int): Merk
}

class NetworkMerkRepository(
    private val merkApiService: merkService
) : MerkRepository {

    override suspend fun getAllMerk(): AllMerkResponse =
        merkApiService.getAllMerk()

    override suspend fun insertMerk(merk: Merk) {
        merkApiService.insertMerk(merk)
    }

    override suspend fun updateMerk(idMerk: Int, merk: Merk) {
        merkApiService.updateMerk(idMerk, merk)
    }

    override suspend fun deleteMerk(idMerk: Int) {
        try {
            val response = merkApiService.deleteMerk(idMerk)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete merk. HTTP Status code: ${response.code()}"
                )
            } else {
                println("Merk deleted successfully: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Error deleting merk: ${e.message}")
            throw e
        }
    }

    override suspend fun getMerkById(idMerk: Int): Merk {
        return try {
            merkApiService.getMerkById(idMerk).data
        } catch (e: Exception) {
            println("Error fetching merk by ID: ${e.message}")
            throw e
        }
    }
}
