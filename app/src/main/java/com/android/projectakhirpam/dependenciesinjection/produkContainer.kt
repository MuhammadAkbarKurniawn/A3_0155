package com.android.projectakhirpam.dependenciesinjection

import com.android.projectakhirpam.repository.*
import com.android.projectakhirpam.service.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val produkRepository: ProdukRepository
    val kategoriRepository: KategoriRepository
    val pemasokRepository: PemasokRepository
    val merkRepository: MerkRepository
}

class ApplicationsContainer : AppContainer {

    // Base URL untuk setiap layanan
    private val baseUrl = "http://10.0.2.2:3000/api/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    // Fungsi untuk membuat Retrofit instance
    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
    }

    // Retrofit instances untuk setiap layanan
    private val retrofitProduk: Retrofit = createRetrofit("produk/")
    private val retrofitKategori: Retrofit = createRetrofit("kategori/")
    private val retrofitPemasok: Retrofit = createRetrofit("pemasok/")
    private val retrofitMerk: Retrofit = createRetrofit("merk/")

    // Service instances
    private val produkService: produkService by lazy {
        retrofitProduk.create(produkService::class.java)
    }
    private val kategoriService: kategoriService by lazy {
        retrofitKategori.create(kategoriService::class.java)
    }
    private val pemasokService: pemasokService by lazy {
        retrofitPemasok.create(pemasokService::class.java)
    }
    private val merkService: merkService by lazy {
        retrofitMerk.create(merkService::class.java)
    }

    // Repository instances
    override val produkRepository: ProdukRepository by lazy {
        NetworkProdukRepository(produkService)
    }
    override val kategoriRepository: KategoriRepository by lazy {
        NetworkKategoriRepository(kategoriService)
    }
    override val pemasokRepository: PemasokRepository by lazy {
        NetworkPemasokRepository(pemasokService)
    }
    override val merkRepository: MerkRepository by lazy {
        NetworkMerkRepository(merkService)
    }
}
