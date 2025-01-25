package com.android.projectakhirpam.ui.viewmodel.merk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.model.Merk
import com.android.projectakhirpam.repository.MerkRepository
import kotlinx.coroutines.launch

class MerkInsertViewModel(private val merk: MerkRepository) : ViewModel() {

    var uiStateMerk by mutableStateOf(InsertUiStateMerk())
        private set

    fun updateInsertMerkState(insertUiEvent:MerkInsertUiEvent) {
        uiStateMerk = InsertUiStateMerk(insertUiEventMerk = insertUiEvent)
    }

    suspend fun insertMerk() {
        viewModelScope.launch {
            try {
                merk.insertMerk(uiStateMerk.insertUiEventMerk.toMerk())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiStateMerk(
    val insertUiEventMerk: MerkInsertUiEvent = MerkInsertUiEvent()
)

data class MerkInsertUiEvent(
    val idMerk: String = "",
    val namaMerk: String = "",
    val deskripsiMerk: String = "",
)

fun MerkInsertUiEvent.toMerk(): Merk = Merk(
    idMerk = idMerk,
    namaMerk = namaMerk,
    deskripsiMerk = deskripsiMerk
)

fun Merk.toUiStateMerk(): InsertUiStateMerk = InsertUiStateMerk(
    insertUiEventMerk = toInsertUiEvent()
)

fun Merk.toInsertUiEvent(): MerkInsertUiEvent = MerkInsertUiEvent(
    idMerk = idMerk,
    namaMerk = namaMerk,
    deskripsiMerk = deskripsiMerk
)
