package com.android.projectakhirpam.ui.view.kategori

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.android.projectakhirpam.ui.customwidget.CostumeTopAppBar
import com.android.projectakhirpam.ui.navigation.DestinasiNavigasi
import com.android.projectakhirpam.ui.view.produk.OnError
import com.android.projectakhirpam.ui.view.produk.OnLoading
import com.android.projectakhirpam.ui.viewmodel.kategori.HomeUiState
import com.android.projectakhirpam.ui.viewmodel.kategori.KategoriViewModel
import com.android.projectakhirpam.ui.viewmodel.penyediaViewModel

object DestinasiHomeKategori : DestinasiNavigasi {
    override val route = "home_kategori"
    override val titleRes = "Home Kategori"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeKategoriScreen(
    navigateBack: () -> Unit,
    navigateToKategoriEntry: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: KategoriViewModel = viewModel(factory = penyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeKategori.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getKategori()
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
        KategoriStatus(
            kategoriUiState = viewModel.ktgrUIState,
            retryAction = { viewModel.getKategori() },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun KategoriStatus(
    kategoriUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (kategoriUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            if (kategoriUiState.kategori.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada data Kategori.")
                }
            } else {
                KategoriList(
                    kategori = kategoriUiState.kategori,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun KategoriList(
    kategori: List<Kategori>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(kategori) { kategoriItem ->
            KategoriCard(kategori = kategoriItem, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun KategoriCard(
    kategori: Kategori,
    modifier: Modifier = Modifier
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
            Text(
                text = kategori.idKategori,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = kategori.namaKategori,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = kategori.deskripsiKategori,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
