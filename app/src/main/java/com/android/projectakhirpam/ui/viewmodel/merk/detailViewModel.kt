package com.android.projectakhirpam.ui.viewmodel.merk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.model.Merk
import com.android.projectakhirpam.repository.MerkRepository
import com.android.projectakhirpam.ui.view.merk.DestinasiDetailMerk
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class DetailMerkState {
    data class Success(val merk: Merk) : DetailMerkState()
    object Error : DetailMerkState()
    object Loading : DetailMerkState()
}

class DetailMerkViewModel (
    savedStateHandle: SavedStateHandle,
    private val merkRepository: MerkRepository
) : ViewModel(){
    var merkDetailState: DetailMerkState by mutableStateOf(DetailMerkState.Loading)
        private set

    private val _idmerk: String = checkNotNull(savedStateHandle[DestinasiDetailMerk.IDMERK])

    init {
        getDetailMerk()
    }

    fun getDetailMerk() {
        viewModelScope.launch {
            merkDetailState = DetailMerkState.Loading
            merkDetailState = try {
                val ktgr = merkRepository.getMerkById(_idmerk)
                DetailMerkState.Success(ktgr)
            } catch (e: IOException) {
                DetailMerkState.Error
            } catch (e: HttpException) {
                DetailMerkState.Error
            }
        }
    }
}