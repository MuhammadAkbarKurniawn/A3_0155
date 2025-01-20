package com.android.projectakhirpam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.android.projectakhirpam.ProdukApplications
import com.android.projectakhirpam.ui.viewmodel.produk.HomeViewModel

object penyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiProduk().container.produkRepository) }
//        initializer { InsertViewModel(aplikasiKontak().container.kontakRepository) }
//        initializer { DetailViewModel(aplikasiKontak().container.kontakRepository) }
//        initializer { UpdateViewModel(aplikasiKontak().container.kontakRepository) }
    }

    fun CreationExtras.aplikasiProduk(): ProdukApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as ProdukApplications)
}