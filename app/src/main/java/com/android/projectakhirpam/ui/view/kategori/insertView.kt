package com.android.projectakhirpam.ui.view.kategori

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.projectakhirpam.ui.customwidget.CostumeTopAppBar
import com.android.projectakhirpam.ui.navigation.DestinasiNavigasi
import com.android.projectakhirpam.ui.viewmodel.kategori.InsertUiStateKtgr
import com.android.projectakhirpam.ui.viewmodel.kategori.KategoriInsertViewModel
import com.android.projectakhirpam.ui.viewmodel.kategori.KtgrInsertUiEvent
import com.android.projectakhirpam.ui.viewmodel.penyediaViewModel
import com.android.projectakhirpam.ui.viewmodel.produk.InsertUiEvent
import com.android.projectakhirpam.ui.viewmodel.produk.InsertUiState
import com.android.projectakhirpam.ui.viewmodel.produk.InsertViewModel
import kotlinx.coroutines.launch


object DestinasiEntryKategori : DestinasiNavigasi {
    override val route = "kategori_entry"
    override val titleRes = "Entry Kategori"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryKategoriScreen(
    navigateBack:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: KategoriInsertViewModel = viewModel(factory = penyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier =modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryKategori.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBody(
            insertUiStateKtgr = viewModel.uiStateKtgr,
            onkategoriValueChange = viewModel ::updateInsertKtgrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertKtgr()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )

    }
}

@Composable
fun EntryBody(
    insertUiStateKtgr: InsertUiStateKtgr,
    onkategoriValueChange:(KtgrInsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            ktgrInsertUiEvent = insertUiStateKtgr.insertUiEventKtgr,
            onValueChange = onkategoriValueChange,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    ktgrInsertUiEvent: KtgrInsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (KtgrInsertUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){

        OutlinedTextField(
            value = ktgrInsertUiEvent.idKategori,
            onValueChange = {onValueChange(ktgrInsertUiEvent.copy(idKategori = it))},
            label = { Text("id Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = ktgrInsertUiEvent.namaKategori,
            onValueChange = {onValueChange(ktgrInsertUiEvent.copy(namaKategori = it))},
            label = { Text("Nama Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = ktgrInsertUiEvent.deskripsiKategori,
            onValueChange = {onValueChange(ktgrInsertUiEvent.copy(deskripsiKategori = it))},
            label = { Text("Deskripsi Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

//        if(enabled){
//            Text(
//                text = "Isi Semua Data!",
//                modifier = Modifier.padding(12.dp)
//            )
//        }
//        Divider(
//            thickness = 8.dp,
//            modifier = Modifier.padding(12.dp)
//        )
    }
}