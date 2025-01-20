package com.android.projectakhirpam.ui.viewmodel.produk

import retrofit2.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.projectakhirpam.model.Produk
import com.android.projectakhirpam.repository.ProdukRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val produk: List<Produk>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModel(private val prdk: ProdukRepository) : ViewModel() {

    var prdkUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    fun getProduk() {
        viewModelScope.launch {
            prdkUIState = HomeUiState.Loading

            try {
                prdkUIState = HomeUiState.Success(prdk.getAllProduk().data)
            } catch (e:IOException) {
                prdkUIState = HomeUiState.Error
            } catch (e:HttpException) {
                prdkUIState = HomeUiState.Error
            }
        }
    }

    fun deleteProduk(idProduk: String){
        viewModelScope.launch{
            try {
                prdk.deleteProduk(idProduk)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }
}

