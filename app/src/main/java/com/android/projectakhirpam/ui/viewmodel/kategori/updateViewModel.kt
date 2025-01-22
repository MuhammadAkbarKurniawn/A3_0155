package com.android.projectakhirpam.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.repository.KategoriRepository
import com.android.projectakhirpam.ui.view.kategori.DestinasiUpdateKategori
import kotlinx.coroutines.launch

class KategoriUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository: KategoriRepository
) : ViewModel() {

    var uiStateKtgr by mutableStateOf(InsertUiStateKtgr())
        private set

    private val _idkategori: String = checkNotNull(savedStateHandle[DestinasiUpdateKategori.IDKTGR])

    init {
        viewModelScope.launch {
            val kategori = kategoriRepository.getKategoriById(_idkategori)
            uiStateKtgr = kategori.toUiStateKtgr()
        }
    }

    fun updateInsertKtgrState(insertUiEvent: KtgrInsertUiEvent) {
        uiStateKtgr = InsertUiStateKtgr(insertUiEventKtgr = insertUiEvent)
    }

    suspend fun updateKtgr() {
        viewModelScope.launch {
            try {
                kategoriRepository.updateKategori(_idkategori, uiStateKtgr.insertUiEventKtgr.toKtgr())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
