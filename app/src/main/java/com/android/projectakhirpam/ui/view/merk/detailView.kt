package com.android.projectakhirpam.ui.view.merk

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
import com.android.projectakhirpam.model.Merk
import com.android.projectakhirpam.model.Pemasok
import com.android.projectakhirpam.ui.customwidget.CostumeTopAppBar
import com.android.projectakhirpam.ui.navigation.DestinasiNavigasi
import com.android.projectakhirpam.ui.viewmodel.merk.DetailMerkState
import com.android.projectakhirpam.ui.viewmodel.merk.DetailMerkViewModel
import com.android.projectakhirpam.ui.viewmodel.penyediaViewModel

object DestinasiDetailMerk : DestinasiNavigasi {
    override val route = "detail merk"
    override val titleRes = "Detail Merk"
    const val IDMERK = "id_merk"
    val routeWithArg = "$route/{$IDMERK}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMerkScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailMerkViewModel = viewModel(factory = penyediaViewModel.Factory)
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailMerk.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getDetailMerk()
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
                    contentDescription = "Edit Merk"
                )
            }
        }
    ) { innerPadding ->
        DetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.merkDetailState,
            retryAction = { viewModel.getDetailMerk() },
        )
    }
}

@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailMerkState,
) {
    when (detailUiState) {
        is DetailMerkState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailMerkState.Success -> {
            if (detailUiState.merk.idMerk.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailMerk(
                    merk = detailUiState.merk,
                    modifier = modifier.fillMaxWidth(),
                )
            }
        }

        is DetailMerkState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailMerk(
    merk: Merk,
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
                ComponentDetailMerk(judul = "Nama Merk", isinya = merk.namaMerk)
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentDetailMerk(judul = "id Merk", isinya = merk.idMerk)
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentDetailMerk(judul = "Deskripsi Merk", isinya = merk.deskripsiMerk)
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Composable
fun ComponentDetailMerk(
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