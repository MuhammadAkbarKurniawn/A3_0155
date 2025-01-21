package com.android.projectakhirpam.ui.viewmodel.produk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.repository.ProdukRepository
import com.android.projectakhirpam.ui.view.produk.DestinasiUpdate
import kotlinx.coroutines.launch

class UpdateViewModel (
    savedStateHandle: SavedStateHandle,
    private val produkRepository: ProdukRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertUiState())
        private set

    private val _idproduk: String = checkNotNull(savedStateHandle[DestinasiUpdate.IDPRODUK])

    init {
        viewModelScope.launch {
            updateUiState = produkRepository.getProdukById(_idproduk)
                .toUiStatePrdk()
        }
    }

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateMhs(){
        viewModelScope.launch {
            try {
                produkRepository.updateProduk(_idproduk, updateUiState.insertUiEvent.toPrdk())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}