package com.android.projectakhirpam.ui.view.pemasok

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.projectakhirpam.model.Kategori
import com.android.projectakhirpam.model.Pemasok
import com.android.projectakhirpam.ui.customwidget.CostumeTopAppBar
import com.android.projectakhirpam.ui.navigation.DestinasiNavigasi
import com.android.projectakhirpam.ui.view.produk.OnError
import com.android.projectakhirpam.ui.view.produk.OnLoading
import com.android.projectakhirpam.ui.viewmodel.pemasok.HomeUiState
import com.android.projectakhirpam.ui.viewmodel.pemasok.PemasokViewModel
import com.android.projectakhirpam.ui.viewmodel.penyediaViewModel

object DestinasiHomePemasok : DestinasiNavigasi {
    override val route = "home_pemasok"
    override val titleRes = "Home Pemasok"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePemasokScreen(
    navigateBack: () -> Unit,
    navigateToKategoriEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick:(String) -> Unit = {},
    viewModel: PemasokViewModel = viewModel(factory = penyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePemasok.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPemasok()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToKategoriEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kategori")
            }
        }
    ) { innerPadding ->
        PemasokStatus(
            pemasokUiState = viewModel.pmskUIState,
            retryAction = { viewModel.getPemasok() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePemasok(it.idPemasok)
                viewModel.getPemasok()
            }
        )
    }
}

@Composable
fun PemasokStatus(
    pemasokUiState: HomeUiState,
    retryAction: () -> Unit,
    onDeleteClick: (Pemasok) -> Unit = {},
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {}
) {
    when (pemasokUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            if (pemasokUiState.pemasok.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada data Kategori.")
                }
            } else {
                PemasokList(
                    pemasok = pemasokUiState.pemasok,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idPemasok)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PemasokList(
    pemasok: List<Pemasok>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pemasok) -> Unit,
    onDeleteClick: (Pemasok) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pemasok) { pemasokItem ->
            PemasokCard(
                pemasok = pemasokItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pemasokItem) }
            ){
                onDeleteClick(pemasokItem)
            }
        }
    }
}

@Composable
fun PemasokCard(
    pemasok: Pemasok,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pemasok) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = pemasok.namaPemasok,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {onDeleteClick(pemasok)}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
            Text(
                text = pemasok.idPemasok,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = pemasok.teleponPemasok,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = pemasok.alamatPemasok,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
