package com.android.projectakhirpam.ui.viewmodel.pemasok

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.model.Pemasok
import com.android.projectakhirpam.repository.PemasokRepository
import com.android.projectakhirpam.ui.view.pemasok.DestinasiDetailPemasok
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class DetailPemasokState {
    data class Success(val pemasok: Pemasok) : DetailPemasokState()
    object Error : DetailPemasokState()
    object Loading : DetailPemasokState()
}

class DetailPemasokViewModel (
    savedStateHandle: SavedStateHandle,
    private val pemasokRepository: PemasokRepository
) : ViewModel(){
    var pmskDetailState: DetailPemasokState by mutableStateOf(DetailPemasokState.Loading)
        private set

    private val _idpemasok: String = checkNotNull(savedStateHandle[DestinasiDetailPemasok.IDPMSK])

    init {
        getDetailPemasok()
    }

    fun getDetailPemasok() {
        viewModelScope.launch {
            pmskDetailState = DetailPemasokState.Loading
            pmskDetailState = try {
                val ktgr = pemasokRepository.getPemasokById(_idpemasok)
                DetailPemasokState.Success(ktgr)
            } catch (e: IOException) {
                DetailPemasokState.Error
            } catch (e: HttpException) {
                DetailPemasokState.Error
            }
        }
    }
}