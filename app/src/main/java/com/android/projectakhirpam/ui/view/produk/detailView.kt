package com.android.projectakhirpam.ui.view.produk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.projectakhirpam.model.Produk
import com.android.projectakhirpam.ui.customwidget.CostumeTopAppBar
import com.android.projectakhirpam.ui.navigation.DestinasiNavigasi
import com.android.projectakhirpam.ui.viewmodel.penyediaViewModel
import com.android.projectakhirpam.ui.viewmodel.produk.DetailUiState
import com.android.projectakhirpam.ui.viewmodel.produk.DetailViewModel

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Produk"
    const val IDPRODUK = "id_produk"
    val routeWithArg = "$route/{$IDPRODUK}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    navigateToHomeKategori: () -> Unit,
    navigateToHomePemasok: () -> Unit,
    navigateToHomeMerk: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = penyediaViewModel.Factory)
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getDetailProduk()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton (
                onClick = navigateToItemUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Produk"
                )
            }
        }
    ) { innerPadding ->
        DetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.produkDetailState,
            retryAction = { viewModel.getDetailProduk() },
            navigateToHomeKategori = navigateToHomeKategori,
            navigateToHomePemasok = navigateToHomePemasok,
            navigateToHomeMerk = navigateToHomeMerk,
            namaKategori = viewModel.namaKategori,
            namaPemasok = viewModel.namaPemasok,
            namaMerk = viewModel.namaMerk
        )
    }
}

@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState,
    navigateToHomeKategori: () -> Unit,
    navigateToHomePemasok: () -> Unit,
    navigateToHomeMerk: () -> Unit,
    namaKategori: String,
    namaPemasok: String,
    namaMerk: String
) {
    when (detailUiState) {
        is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailUiState.Success -> {
            if (detailUiState.produk.idProduk.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailPrdk(
                    produk = detailUiState.produk,
                    modifier = modifier.fillMaxWidth(),
                    navigateToHomeKategori = navigateToHomeKategori,
                    navigateToHomePemasok = navigateToHomePemasok,
                    navigateToHomeMerk = navigateToHomeMerk,
                    namaKategori = namaKategori,
                    namaPemasok = namaPemasok,
                    namaMerk = namaMerk
                )
            }
        }

        is DetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPrdk(
    produk: Produk,
    modifier: Modifier,
    navigateToHomeKategori: () -> Unit,
    navigateToHomePemasok: () -> Unit,
    navigateToHomeMerk: () -> Unit,
    namaKategori: String,
    namaPemasok: String,
    namaMerk: String
) {
    Column(
        modifier = Modifier
            .padding(15.dp),
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ComponentDetailProduk(judul = "Nama Produk", isinya = produk.namaProduk)
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentDetailProduk(judul = "id Produk", isinya = produk.idProduk)
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentDetailProduk(judul = "deskripsi", isinya = produk.deskripsiProduk)
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentDetailProduk(judul = "Stok", isinya = produk.stok)
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentDetailProduk(judul = "Harga", isinya = "Rp. ${produk.harga}")
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentDetailProduk(judul = "Kategori", isinya = namaKategori)
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentDetailProduk(judul = "Pemasok", isinya = namaPemasok)
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentDetailProduk(judul = "Merk", isinya = namaMerk)
            }
        }
        Button(
            onClick = navigateToHomeKategori,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Kategori terkait Produk")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = navigateToHomePemasok,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Pemasok")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = navigateToHomeMerk,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Merk")
        }
    }
}
@Composable
fun ComponentDetailProduk(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}