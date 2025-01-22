package com.android.projectakhirpam.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.model.Kategori
import com.android.projectakhirpam.model.Produk
import com.android.projectakhirpam.repository.KategoriRepository
import com.android.projectakhirpam.repository.ProdukRepository
import com.android.projectakhirpam.ui.view.kategori.DestinasiDetailKategori
import com.android.projectakhirpam.ui.view.produk.DestinasiDetail
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class DetailkategoriState {
    data class Success(val kategori: Kategori) : DetailkategoriState()
    object Error : DetailkategoriState()
    object Loading : DetailkategoriState()
}

class DetailKategoriViewModel (
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository: KategoriRepository
) : ViewModel(){
    var ktgrDetailState: DetailkategoriState by mutableStateOf(DetailkategoriState.Loading)
        private set

    private val _idkategori: String = checkNotNull(savedStateHandle[DestinasiDetailKategori.IDKTGR])

    init {
        getDetailKategori()
    }

    fun getDetailKategori() {
        viewModelScope.launch {
            ktgrDetailState = DetailkategoriState.Loading
            ktgrDetailState = try {
                val ktgr = kategoriRepository.getKategoriById(_idkategori)
                DetailkategoriState.Success(ktgr)
            } catch (e: IOException) {
                DetailkategoriState.Error
            } catch (e: HttpException) {
                DetailkategoriState.Error
            }
        }
    }
}