package com.android.projectakhirpam.ui.view.merk

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
import com.android.projectakhirpam.ui.viewmodel.merk.InsertUiStateMerk
import com.android.projectakhirpam.ui.viewmodel.merk.MerkInsertUiEvent
import com.android.projectakhirpam.ui.viewmodel.merk.MerkInsertViewModel
import com.android.projectakhirpam.ui.viewmodel.penyediaViewModel
import kotlinx.coroutines.launch


object DestinasiEntryMerk : DestinasiNavigasi {
    override val route = "merk_entry"
    override val titleRes = "Entry Merk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMerkScreen(
    navigateBack:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: MerkInsertViewModel = viewModel(factory = penyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryMerk.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBodyMerk(
            insertUiStateMerk = viewModel.uiStateMerk,
            onMerkValueChange = viewModel ::updateInsertMerkState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertMerk()
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
fun EntryBodyMerk(
    insertUiStateMerk: InsertUiStateMerk,
    onMerkValueChange:(MerkInsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            merkInsertUiEvent = insertUiStateMerk.insertUiEventMerk,
            onValueChange = onMerkValueChange,
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
    merkInsertUiEvent: MerkInsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (MerkInsertUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){

        OutlinedTextField(
            value = merkInsertUiEvent.idMerk,
            onValueChange = {onValueChange(merkInsertUiEvent.copy(idMerk = it))},
            label = { Text("id Merk") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = merkInsertUiEvent.namaMerk,
            onValueChange = {onValueChange(merkInsertUiEvent.copy(namaMerk = it))},
            label = { Text("Nama Merk") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = merkInsertUiEvent.deskripsiMerk,
            onValueChange = {onValueChange(merkInsertUiEvent.copy(deskripsiMerk = it))},
            label = { Text("Deskripsi Merk") },
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