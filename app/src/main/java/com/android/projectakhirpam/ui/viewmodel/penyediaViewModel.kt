package com.android.projectakhirpam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.android.projectakhirpam.ProdukApplications
import com.android.projectakhirpam.ui.viewmodel.kategori.DetailKategoriViewModel
import com.android.projectakhirpam.ui.viewmodel.kategori.KategoriInsertViewModel
import com.android.projectakhirpam.ui.viewmodel.kategori.KategoriUpdateViewModel
import com.android.projectakhirpam.ui.viewmodel.kategori.KategoriViewModel
import com.android.projectakhirpam.ui.viewmodel.merk.DetailMerkViewModel
import com.android.projectakhirpam.ui.viewmodel.merk.MerkInsertViewModel
import com.android.projectakhirpam.ui.viewmodel.merk.MerkUpdateViewModel
import com.android.projectakhirpam.ui.viewmodel.merk.MerkViewModel
import com.android.projectakhirpam.ui.viewmodel.pemasok.DetailPemasokViewModel
import com.android.projectakhirpam.ui.viewmodel.pemasok.PemasokInsertViewModel
import com.android.projectakhirpam.ui.viewmodel.pemasok.PemasokUpdateViewModel
import com.android.projectakhirpam.ui.viewmodel.pemasok.PemasokViewModel
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
        initializer { DetailKategoriViewModel(createSavedStateHandle(),aplikasiProduk().container.kategoriRepository) }
        initializer { KategoriUpdateViewModel(createSavedStateHandle(),aplikasiProduk().container.kategoriRepository) }

        initializer { PemasokViewModel(aplikasiProduk().container.pemasokRepository) }
        initializer { PemasokInsertViewModel(aplikasiProduk().container.pemasokRepository) }
        initializer { DetailPemasokViewModel(createSavedStateHandle(),aplikasiProduk().container.pemasokRepository) }
        initializer { PemasokUpdateViewModel(createSavedStateHandle(),aplikasiProduk().container.pemasokRepository) }

        initializer { MerkViewModel(aplikasiProduk().container.merkRepository) }
        initializer { MerkInsertViewModel(aplikasiProduk().container.merkRepository) }
        initializer { DetailMerkViewModel(createSavedStateHandle(),aplikasiProduk().container.merkRepository) }
        initializer { MerkUpdateViewModel(createSavedStateHandle(),aplikasiProduk().container.merkRepository) }
    }

    fun CreationExtras.aplikasiProduk(): ProdukApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as ProdukApplications)
}