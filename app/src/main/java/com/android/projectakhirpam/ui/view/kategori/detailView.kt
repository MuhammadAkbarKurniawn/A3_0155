package com.android.projectakhirpam.ui.view.kategori

import androidx.compose.foundation.layout.Arrangement
import com.android.projectakhirpam.ui.view.produk.OnError
import com.android.projectakhirpam.ui.view.produk.OnLoading
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import com.android.projectakhirpam.model.Kategori
import com.android.projectakhirpam.ui.customwidget.CostumeTopAppBar
import com.android.projectakhirpam.ui.navigation.DestinasiNavigasi
import com.android.projectakhirpam.ui.viewmodel.kategori.DetailKategoriViewModel
import com.android.projectakhirpam.ui.viewmodel.kategori.DetailkategoriState
import com.android.projectakhirpam.ui.viewmodel.penyediaViewModel

object DestinasiDetailKategori : DestinasiNavigasi {
    override val route = "detail kategori"
    override val titleRes = "Detail kategori"
    const val IDKTGR = "id_kategori"
    val routeWithArg = "$route/{$IDKTGR}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKategoriScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailKategoriViewModel = viewModel(factory = penyediaViewModel.Factory)
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailKategori.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getDetailKategori()
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
                    contentDescription = "Edit Kategori"
                )
            }
        }
    ) { innerPadding ->
        DetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.ktgrDetailState,
            retryAction = { viewModel.getDetailKategori() },
        )
    }
}

@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailkategoriState,
) {
    when (detailUiState) {
        is DetailkategoriState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailkategoriState.Success -> {
            if (detailUiState.kategori.idKategori.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailKtgr(
                    kategori = detailUiState.kategori,
                    modifier = modifier.fillMaxWidth(),
                )
            }
        }

        is DetailkategoriState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailKtgr(
    kategori: Kategori,
    modifier: Modifier,
){
    Column(
    modifier = modifier
        .fillMaxSize()
        .padding(15.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)){
        Card (
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ){
            Column(modifier = Modifier.padding(16.dp)) {
                ComponentDetailProduk(judul = "Nama kategori", isinya = kategori.namaKategori)
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentDetailProduk(judul = "id Kategori", isinya = kategori.idKategori)
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentDetailProduk(judul = "Deskripsi", isinya = kategori.deskripsiKategori)
            }
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