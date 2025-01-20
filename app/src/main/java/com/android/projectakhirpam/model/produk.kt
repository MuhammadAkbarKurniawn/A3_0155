package com.android.projectakhirpam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Data class untuk Produk
@Serializable
data class Produk(
    @SerialName("id_produk")
    val idProduk: Int,

    @SerialName("nama_produk")
    val namaProduk: String,

    @SerialName("deskripsi_produk")
    val deskripsiProduk: String,

    val harga: Double,
    val stok: Int,

    @SerialName("id_kategori")
    val idKategori: Int,

    @SerialName("id_pemasok")
    val idPemasok: Int,

    @SerialName("id_merk")
    val idMerk: Int
)

// Data class untuk Kategori
@Serializable
data class Kategori(
    @SerialName("id_kategori")
    val idKategori: Int,

    @SerialName("nama_kategori")
    val namaKategori: String,

    @SerialName("deskripsi_kategori")
    val deskripsiKategori: String
)

// Data class untuk Pemasok
@Serializable
data class Pemasok(
    @SerialName("id_pemasok")
    val idPemasok: Int,

    @SerialName("nama_pemasok")
    val namaPemasok: String,

    @SerialName("alamat_pemasok")
    val alamatPemasok: String,

    @SerialName("telepon_pemasok")
    val teleponPemasok: String
)

// Data class untuk Merk
@Serializable
data class Merk(
    @SerialName("id_merk")
    val idMerk: Int,

    @SerialName("nama_merk")
    val namaMerk: String,

    @SerialName("deskripsi_merk")
    val deskripsiMerk: String
)

// Response untuk semua produk
@Serializable
data class AllProdukResponse(
    val status: Boolean,
    val message: String,
    val data: List<Produk>
)

// Response untuk detail produk
@Serializable
data class ProdukDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Produk
)

// Response untuk semua kategori
@Serializable
data class AllKategoriResponse(
    val status: Boolean,
    val message: String,
    val data: List<Kategori>
)

// Response untuk detail kategori
@Serializable
data class KategoriDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Kategori
)

// Response untuk semua pemasok
@Serializable
data class AllPemasokResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pemasok>
)

// Response untuk detail pemasok
@Serializable
data class PemasokDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pemasok
)

// Response untuk semua merk
@Serializable
data class AllMerkResponse(
    val status: Boolean,
    val message: String,
    val data: List<Merk>
)

// Response untuk detail merk
@Serializable
data class MerkDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Merk
)
