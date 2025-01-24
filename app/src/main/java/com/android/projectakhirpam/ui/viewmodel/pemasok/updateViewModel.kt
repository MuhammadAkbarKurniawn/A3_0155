package com.android.projectakhirpam.ui.viewmodel.pemasok

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.repository.PemasokRepository
import com.android.projectakhirpam.ui.view.pemasok.DestinasiUpdatePemasok
import kotlinx.coroutines.launch

class PemasokUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val pemasokRepository: PemasokRepository
) : ViewModel() {

    var uiStatePmsk by mutableStateOf(InsertUiStatePmsk())
        private set

    private val _idpemasok: String = checkNotNull(savedStateHandle[DestinasiUpdatePemasok.IDPMSK])

    init {
        viewModelScope.launch {
            val pemasok = pemasokRepository.getPemasokById(_idpemasok)
            uiStatePmsk = pemasok.toUiStatePmsk()
        }
    }

    fun updateInsertPmskState(insertUiEvent: PmskInsertUiEvent) {
        uiStatePmsk = InsertUiStatePmsk(insertUiEventPmsk = insertUiEvent)
    }

    suspend fun updatePmsk() {
        viewModelScope.launch {
            try {
                pemasokRepository.updatePemasok(_idpemasok, uiStatePmsk.insertUiEventPmsk.toPmsk())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
