package com.android.projectakhirpam.ui.viewmodel.merk

import retrofit2.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.model.Merk
import com.android.projectakhirpam.repository.MerkRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val merk: List<Merk>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class MerkViewModel(private val mrk: MerkRepository) : ViewModel() {

    var merkUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    fun getMerk() {
        viewModelScope.launch {
            merkUIState = HomeUiState.Loading

            try {
                merkUIState = HomeUiState.Success(mrk.getAllMerk().data)
            } catch (e:IOException) {
                merkUIState = HomeUiState.Error
            } catch (e:HttpException) {
                merkUIState = HomeUiState.Error
            }
        }
    }

    fun deleteMerk(idMerk: String){
        viewModelScope.launch{
            try {
                mrk.deleteMerk(idMerk)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }
}

