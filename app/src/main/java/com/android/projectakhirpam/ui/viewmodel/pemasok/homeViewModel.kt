package com.android.projectakhirpam.ui.viewmodel.pemasok

import retrofit2.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.model.Pemasok
import com.android.projectakhirpam.repository.PemasokRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val pemasok: List<Pemasok>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class PemasokViewModel(private val pmsk: PemasokRepository) : ViewModel() {

    var pmskUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    fun getPemasok() {
        viewModelScope.launch {
            pmskUIState = HomeUiState.Loading

            try {
                pmskUIState = HomeUiState.Success(pmsk.getAllPemasok().data)
            } catch (e:IOException) {
                pmskUIState = HomeUiState.Error
            } catch (e:HttpException) {
                pmskUIState = HomeUiState.Error
            }
        }
    }

    fun deletePemasok(idPemasok: String){
        viewModelScope.launch{
            try {
                pmsk.deletePemasok(idPemasok)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }
}

