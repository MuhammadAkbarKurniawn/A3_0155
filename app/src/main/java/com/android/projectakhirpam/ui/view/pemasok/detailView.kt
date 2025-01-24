package com.android.projectakhirpam.ui.view.pemasok

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
import com.android.projectakhirpam.model.Pemasok
import com.android.projectakhirpam.ui.customwidget.CostumeTopAppBar
import com.android.projectakhirpam.ui.navigation.DestinasiNavigasi
import com.android.projectakhirpam.ui.viewmodel.pemasok.DetailPemasokState
import com.android.projectakhirpam.ui.viewmodel.pemasok.DetailPemasokViewModel
import com.android.projectakhirpam.ui.viewmodel.penyediaViewModel

object DestinasiDetailPemasok : DestinasiNavigasi {
    override val route = "detail pemasok"
    override val titleRes = "Detail Pemasok"
    const val IDPMSK = "id_pemasok"
    val routeWithArg = "$route/{$IDPMSK}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPemasokScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPemasokViewModel = viewModel(factory = penyediaViewModel.Factory)
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPemasok.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getDetailPemasok()
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
                    contentDescription = "Edit Pemasok"
                )
            }
        }
    ) { innerPadding ->
        DetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.pmskDetailState,
            retryAction = { viewModel.getDetailPemasok() },
        )
    }
}

@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailPemasokState,
) {
    when (detailUiState) {
        is DetailPemasokState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailPemasokState.Success -> {
            if (detailUiState.pemasok.idPemasok.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailPemasok(
                    pemasok = detailUiState.pemasok,
                    modifier = modifier.fillMaxWidth(),
                )
            }
        }

        is DetailPemasokState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPemasok(
    pemasok: Pemasok,
    modifier: Modifier,
){
    Column(
        modifier = Modifier
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
                ComponentDetailPemasok(judul = "Nama Pemasok", isinya = pemasok.namaPemasok)
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentDetailPemasok(judul = "id Pemasok", isinya = pemasok.idPemasok)
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentDetailPemasok(judul = "Telepon Pemasok", isinya = pemasok.teleponPemasok)
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentDetailPemasok(judul = "Alamat Pemasok", isinya = pemasok.alamatPemasok)
            }
        }
    }
}

@Composable
fun ComponentDetailPemasok(
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