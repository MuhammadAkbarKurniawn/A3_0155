package com.android.projectakhirpam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.android.projectakhirpam.ProdukApplications
import com.android.projectakhirpam.ui.viewmodel.kategori.KategoriInsertViewModel
import com.android.projectakhirpam.ui.viewmodel.kategori.KategoriViewModel
import com.android.projectakhirpam.ui.viewmodel.produk.DetailViewModel
import com.android.projectakhirpam.ui.viewmodel.produk.HomeViewModel
import com.android.projectakhirpam.ui.viewmodel.produk.InsertViewModel
import com.android.projectakhirpam.ui.viewmodel.produk.UpdateViewModel

object penyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiProduk().container.produkRepository) }
        initializer { InsertViewModel(aplikasiProduk().container.produkRepository) }
        initializer { DetailViewModel(createSavedStateHandle(),aplikasiProduk().container.produkRepository) }
        initializer { UpdateViewModel(createSavedStateHandle(),aplikasiProduk().container.produkRepository) }

        initializer { KategoriViewModel(aplikasiProduk().container.kategoriRepository) }
        initializer { KategoriInsertViewModel(aplikasiProduk().container.kategoriRepository) }
    }

    fun CreationExtras.aplikasiProduk(): ProdukApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as ProdukApplications)
}