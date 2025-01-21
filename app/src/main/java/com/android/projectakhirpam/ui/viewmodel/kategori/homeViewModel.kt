package com.android.projectakhirpam.ui.viewmodel.kategori

import retrofit2.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.model.Kategori
import com.android.projectakhirpam.repository.KategoriRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val kategori: List<Kategori>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class KategoriViewModel(private val ktgr: KategoriRepository) : ViewModel() {

    var ktgrUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    fun getKategori() {
        viewModelScope.launch {
            ktgrUIState = HomeUiState.Loading

            try {
                ktgrUIState = HomeUiState.Success(ktgr.getAllKategori().data)
            } catch (e:IOException) {
                ktgrUIState = HomeUiState.Error
            } catch (e:HttpException) {
                ktgrUIState = HomeUiState.Error
            }
        }
    }

    fun deleteKategori(idKategori: String){
        viewModelScope.launch{
            try {
                ktgr.deleteKategori(idKategori)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }
}

