package com.android.projectakhirpam.ui.viewmodel.produk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.model.Merk
import com.android.projectakhirpam.model.Produk
import com.android.projectakhirpam.repository.KategoriRepository
import com.android.projectakhirpam.repository.MerkRepository
import com.android.projectakhirpam.repository.PemasokRepository
import com.android.projectakhirpam.repository.ProdukRepository
import com.android.projectakhirpam.ui.view.produk.DestinasiDetail
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class DetailUiState {
    data class Success(val produk: Produk) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailViewModel (
    savedStateHandle: SavedStateHandle,
    private val produkRepository: ProdukRepository,
    private val kategoriRepository: KategoriRepository,
    private val pemasokRepository: PemasokRepository,
    private val merkRepository: MerkRepository
) : ViewModel(){
    var produkDetailState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    var namaKategori: String by mutableStateOf("")
        private set
    var namaPemasok: String by mutableStateOf("")
        private set
    var namaMerk: String by mutableStateOf("")
        private set

    private val _idproduk: String = checkNotNull(savedStateHandle[DestinasiDetail.IDPRODUK])

    init {
        getDetailProduk()
    }

    fun getDetailProduk() {
        viewModelScope.launch {
            produkDetailState = DetailUiState.Loading
            try {
                val produk = produkRepository.getProdukById(_idproduk)
                produkDetailState = DetailUiState.Success(produk)

                // Ambil data kategori, pemasok, dan merk
                val kategori = kategoriRepository.getKategoriById(produk.idKategori)
                val pemasok = pemasokRepository.getPemasokById(produk.idPemasok)
                val merk = merkRepository.getMerkById(produk.idMerk)

                // Ambil nama untuk masing-masing
                namaKategori = kategori.namaKategori
                namaPemasok = pemasok.namaPemasok
                namaMerk = merk.namaMerk


            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }
}