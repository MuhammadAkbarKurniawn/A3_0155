package com.android.projectakhirpam.repository


import com.android.projectakhirpam.model.*
import com.android.projectakhirpam.service.pemasokService
import java.io.IOException

interface PemasokRepository {

    suspend fun getAllPemasok(): AllPemasokResponse

    suspend fun insertPemasok(pemasok: Pemasok)

    suspend fun updatePemasok(idPemasok: Int, pemasok: Pemasok)

    suspend fun deletePemasok(idPemasok: Int)

    suspend fun getPemasokById(idPemasok: Int): Pemasok
}

class NetworkPemasokRepository(
    private val pemasokApiService: pemasokService
) : PemasokRepository {

    override suspend fun getAllPemasok(): AllPemasokResponse =
        pemasokApiService.getAllPemasok()

    override suspend fun insertPemasok(pemasok: Pemasok) {
        pemasokApiService.insertPemasok(pemasok)
    }

    override suspend fun updatePemasok(idPemasok: Int, pemasok: Pemasok) {
        pemasokApiService.updatePemasok(idPemasok, pemasok)
    }

    override suspend fun deletePemasok(idPemasok: Int) {
        try {
            val response = pemasokApiService.deletePemasok(idPemasok)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete pemasok. HTTP Status code: ${response.code()}"
                )
            } else {
                println("Pemasok deleted successfully: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Error deleting pemasok: ${e.message}")
            throw e
        }
    }

    override suspend fun getPemasokById(idPemasok: Int): Pemasok {
        return try {
            pemasokApiService.getPemasokById(idPemasok).data
        } catch (e: Exception) {
            println("Error fetching pemasok by ID: ${e.message}")
            throw e
        }
    }
}
