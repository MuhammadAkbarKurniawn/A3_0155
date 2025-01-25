package com.android.projectakhirpam.ui.view.produk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.projectakhirpam.model.Kategori
import com.android.projectakhirpam.model.Merk
import com.android.projectakhirpam.model.Pemasok
import com.android.projectakhirpam.ui.customwidget.CostumeTopAppBar
import com.android.projectakhirpam.ui.customwidget.DropdownSelector
import com.android.projectakhirpam.ui.navigation.DestinasiNavigasi
import com.android.projectakhirpam.ui.viewmodel.penyediaViewModel
import com.android.projectakhirpam.ui.viewmodel.produk.InsertUiEvent
import com.android.projectakhirpam.ui.viewmodel.produk.InsertUiState
import com.android.projectakhirpam.ui.viewmodel.produk.InsertViewModel
import kotlinx.coroutines.launch


object DestinasiEntry : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Entry Produk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryProdukScreen(
    navigateBack:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = penyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    remember {
        coroutineScope.launch {
            viewModel.LoadData()
        }
    }

    val kategoriList = viewModel.kategoriList
    val pemasokList = viewModel.pemasokList
    val merkList = viewModel.merkList

    Scaffold (
        modifier =modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onProdukValueChange = viewModel :: updateInsertPrdkState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPrdk()
                    viewModel.LoadData()
                    navigateBack()
                }
            },
            kategoriList = kategoriList,
            pemasokList = pemasokList,
            merkList = merkList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )

    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onProdukValueChange:(InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    kategoriList: List<Kategori>,
    pemasokList: List<Pemasok>,
    merkList: List<Merk>
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onProdukValueChange,
            kategoriList = kategoriList,
            pemasokList = pemasokList,
            merkList = merkList,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true,
    kategoriList: List<Kategori>,
    pemasokList: List<Pemasok>,
    merkList: List<Merk>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.namaProduk,
            onValueChange = { onValueChange(insertUiEvent.copy(namaProduk = it)) },
            label = { Text("Nama Produk") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.idProduk,
            onValueChange = { onValueChange(insertUiEvent.copy(idProduk = it)) },
            label = { Text("ID Produk") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsiProduk,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiProduk = it)) },
            label = { Text("Deskripsi Produk") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.stok,
            onValueChange = { onValueChange(insertUiEvent.copy(stok = it)) },
            label = { Text("Stok") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.harga,
            onValueChange = { onValueChange(insertUiEvent.copy(harga = it)) },
            label = { Text("Harga") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Dropdown for Kategori
        DropdownSelector(
            label = "Kategori",
            items = kategoriList.map { it.namaKategori },
            selectedItem = kategoriList.find { it.idKategori == insertUiEvent.idKategori }?.namaKategori.orEmpty(),
            onItemSelected = { selected ->
                val selectedKategori = kategoriList.find { it.namaKategori == selected }
                onValueChange(insertUiEvent.copy(idKategori = selectedKategori?.idKategori.orEmpty()))
            },
            enabled = enabled
        )

        // Dropdown for Pemasok
        DropdownSelector(
            label = "Pemasok",
            items = pemasokList.map { it.namaPemasok },
            selectedItem = pemasokList.find { it.idPemasok == insertUiEvent.idPemasok }?.namaPemasok.orEmpty(),
            onItemSelected = { selected ->
                val selectedPemasok = pemasokList.find { it.namaPemasok == selected }
                onValueChange(insertUiEvent.copy(idPemasok = selectedPemasok?.idPemasok.orEmpty()))
            },
            enabled = enabled
        )

        // Dropdown for Merk
        DropdownSelector(
            label = "Merk",
            items = merkList.map { it.namaMerk },
            selectedItem = merkList.find { it.idMerk == insertUiEvent.idMerk }?.namaMerk.orEmpty(),
            onItemSelected = { selected ->
                val selectedMerk = merkList.find { it.namaMerk == selected }
                onValueChange(insertUiEvent.copy(idMerk = selectedMerk?.idMerk.orEmpty()))
            },
            enabled = enabled
        )
    }
}
