package com.android.projectakhirpam.ui.viewmodel.produk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.model.Kategori
import com.android.projectakhirpam.model.Merk
import com.android.projectakhirpam.model.Pemasok
import com.android.projectakhirpam.model.Produk
import com.android.projectakhirpam.repository.KategoriRepository
import com.android.projectakhirpam.repository.MerkRepository
import com.android.projectakhirpam.repository.PemasokRepository
import com.android.projectakhirpam.repository.ProdukRepository
import kotlinx.coroutines.launch

class InsertViewModel(
    private val prdk: ProdukRepository,
    private val ktgr: KategoriRepository,
    private val pmsk: PemasokRepository,
    private val merk: MerkRepository
    ) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    var kategoriList by mutableStateOf<List<Kategori>>(emptyList())
        private set
    var pemasokList by mutableStateOf<List<Pemasok>>(emptyList())
        private set
    var merkList by mutableStateOf<List<Merk>>(emptyList())
        private set

    fun updateInsertPrdkState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertPrdk() {
        viewModelScope.launch {
            try {
                prdk.insertProduk(uiState.insertUiEvent.toPrdk())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun LoadData(){
        viewModelScope.launch {
            try {
                val kategoriResponse = ktgr.getAllKategori()
                kategoriList = kategoriResponse.data
                val pemasokResponse = pmsk.getAllPemasok()
                pemasokList = pemasokResponse.data
                val merkResponse = merk.getAllMerk()
                merkList = merkResponse.data
            }catch (e: Exception){
            }
        }
    }

    fun PostData(){
        viewModelScope.launch {
            try {
                prdk.insertProduk(uiState.insertUiEvent.toPrdk())
            }catch (e: Exception){

            }
        }
    }
}


data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idProduk: String = "",
    val namaProduk: String = "",
    val deskripsiProduk: String = "",
    val harga: String = "",
    val stok: String = "",
    val idKategori: String = "",
    val idPemasok: String = "",
    val idMerk: String = "",
)

fun InsertUiEvent.toPrdk(): Produk = Produk(
    idProduk = idProduk,
    namaProduk = namaProduk,
    deskripsiProduk = deskripsiProduk,
    harga = harga,
    stok = stok,
    idKategori = idKategori,
    idPemasok = idPemasok,
    idMerk = idMerk
)

fun Produk.toUiStatePrdk(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Produk.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idProduk = idProduk,
    namaProduk = namaProduk,
    deskripsiProduk = deskripsiProduk,
    harga = harga,
    stok = stok,
    idKategori = idKategori,
    idPemasok = idPemasok,
    idMerk = idMerk
)
