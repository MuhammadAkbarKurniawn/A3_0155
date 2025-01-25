package com.android.projectakhirpam.ui.viewmodel.merk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.repository.MerkRepository
import com.android.projectakhirpam.ui.view.merk.DestinasiUpdateMerk
import kotlinx.coroutines.launch

class MerkUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val merkRepository: MerkRepository
) : ViewModel() {

    var uiStateMerk by mutableStateOf(InsertUiStateMerk())
        private set

    private val _idmerk: String = checkNotNull(savedStateHandle[DestinasiUpdateMerk.IDMERK])

    init {
        viewModelScope.launch {
            val merk = merkRepository.getMerkById(_idmerk)
            uiStateMerk = merk.toUiStateMerk()
        }
    }

    fun updateInsertMerkState(insertUiEvent: MerkInsertUiEvent) {
        uiStateMerk = InsertUiStateMerk(insertUiEventMerk = insertUiEvent)
    }

    suspend fun updateMerk() {
        viewModelScope.launch {
            try {
                merkRepository.updateMerk(_idmerk, uiStateMerk.insertUiEventMerk.toMerk())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
