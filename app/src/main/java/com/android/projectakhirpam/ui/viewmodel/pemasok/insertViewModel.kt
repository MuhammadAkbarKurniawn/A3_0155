package com.android.projectakhirpam.ui.viewmodel.pemasok

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.model.Pemasok
import com.android.projectakhirpam.repository.PemasokRepository
import kotlinx.coroutines.launch

class PemasokInsertViewModel(private val pmsk: PemasokRepository) : ViewModel() {

    var uiStatePmsk by mutableStateOf(InsertUiStatePmsk())
        private set

    fun updateInsertPmskState(insertUiEvent:PmskInsertUiEvent) {
        uiStatePmsk = InsertUiStatePmsk(insertUiEventPmsk = insertUiEvent)
    }

    suspend fun insertPmsk() {
        viewModelScope.launch {
            try {
                pmsk.insertPemasok(uiStatePmsk.insertUiEventPmsk.toPmsk())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiStatePmsk(
    val insertUiEventPmsk: PmskInsertUiEvent = PmskInsertUiEvent()
)

data class PmskInsertUiEvent(
    val idPemasok: String = "",
    val namaPemasok: String = "",
    val alamatPemasok: String = "",
    val teleponPemasok: String = "",
)

fun PmskInsertUiEvent.toPmsk(): Pemasok = Pemasok(
    idPemasok = idPemasok,
    namaPemasok = namaPemasok,
    alamatPemasok = alamatPemasok,
    teleponPemasok = teleponPemasok
)

fun Pemasok.toUiStatePmsk(): InsertUiStatePmsk = InsertUiStatePmsk(
    insertUiEventPmsk = toInsertUiEvent()
)

fun Pemasok.toInsertUiEvent(): PmskInsertUiEvent = PmskInsertUiEvent(
    idPemasok = idPemasok,
    namaPemasok = namaPemasok,
    alamatPemasok = alamatPemasok,
    teleponPemasok = teleponPemasok
)
