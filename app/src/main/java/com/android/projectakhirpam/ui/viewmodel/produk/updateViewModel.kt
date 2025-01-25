package com.android.projectakhirpam.ui.viewmodel.produk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.model.Kategori
import com.android.projectakhirpam.model.Merk
import com.android.projectakhirpam.model.Pemasok
import com.android.projectakhirpam.repository.KategoriRepository
import com.android.projectakhirpam.repository.MerkRepository
import com.android.projectakhirpam.repository.PemasokRepository
import com.android.projectakhirpam.repository.ProdukRepository
import com.android.projectakhirpam.ui.view.produk.DestinasiUpdate
import kotlinx.coroutines.launch

class UpdateViewModel (
    savedStateHandle: SavedStateHandle,
    private val produkRepository: ProdukRepository,
    private val kategoriRepository: KategoriRepository,
    private val pemasokRepository: PemasokRepository,
    private val merkRepository: MerkRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertUiState())
        private set

    var kategoriList by mutableStateOf<List<Kategori>>(emptyList())
        private set

    var pemasokList by mutableStateOf<List<Pemasok>>(emptyList())
        private set

    var merkList by mutableStateOf<List<Merk>>(emptyList())
        private set

    private val _idproduk: String = checkNotNull(savedStateHandle[DestinasiUpdate.IDPRODUK])

    init {
        viewModelScope.launch {
            updateUiState = produkRepository.getProdukById(_idproduk)
                .toUiStatePrdk()

            // Fetch dropdown data
            kategoriList = kategoriRepository.getAllKategori().data
            pemasokList = pemasokRepository.getAllPemasok().data
            merkList = merkRepository.getAllMerk().data

        }
    }

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updatePrdk(){
        viewModelScope.launch {
            try {
                produkRepository.updateProduk(_idproduk, updateUiState.insertUiEvent.toPrdk())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}