package com.android.projectakhirpam.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.model.Kategori
import com.android.projectakhirpam.repository.KategoriRepository
import kotlinx.coroutines.launch

class KategoriInsertViewModel(private val ktgr: KategoriRepository) : ViewModel() {

    var uiStateKtgr by mutableStateOf(InsertUiStateKtgr())
        private set

    fun updateInsertKtgrState(insertUiEvent: KtgrInsertUiEvent) {
        uiStateKtgr = InsertUiStateKtgr(insertUiEventKtgr = insertUiEvent)
    }

    suspend fun insertKtgr() {
        viewModelScope.launch {
            try {
                ktgr.insertKategori(uiStateKtgr.insertUiEventKtgr.toKtgr())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiStateKtgr(
    val insertUiEventKtgr: KtgrInsertUiEvent = KtgrInsertUiEvent()
)

data class KtgrInsertUiEvent(
    val idKategori: String = "",
    val namaKategori: String = "",
    val deskripsiKategori: String = "",
)

fun KtgrInsertUiEvent.toKtgr(): Kategori = Kategori(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori
)

fun Kategori.toUiStateKtgr(): InsertUiStateKtgr = InsertUiStateKtgr(
    insertUiEventKtgr = toInsertUiEvent()
)

fun Kategori.toInsertUiEvent(): KtgrInsertUiEvent = KtgrInsertUiEvent(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori
)
