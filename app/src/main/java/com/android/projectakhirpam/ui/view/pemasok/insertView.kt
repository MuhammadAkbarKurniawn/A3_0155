package com.android.projectakhirpam.ui.view.pemasok

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
import com.android.projectakhirpam.ui.viewmodel.pemasok.InsertUiStatePmsk
import com.android.projectakhirpam.ui.viewmodel.pemasok.PemasokInsertViewModel
import com.android.projectakhirpam.ui.viewmodel.pemasok.PmskInsertUiEvent
import com.android.projectakhirpam.ui.viewmodel.penyediaViewModel
import kotlinx.coroutines.launch


object DestinasiEntryPemasok : DestinasiNavigasi {
    override val route = "pemasok_entry"
    override val titleRes = "Entry Pemasok"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPemasokScreen(
    navigateBack:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: PemasokInsertViewModel = viewModel(factory = penyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryPemasok.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBodyPmsk(
            insertUiStatePmsk = viewModel.uiStatePmsk,
            onpemasokValueChange = viewModel ::updateInsertPmskState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPmsk()
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
fun EntryBodyPmsk(
    insertUiStatePmsk: InsertUiStatePmsk,
    onpemasokValueChange:(PmskInsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            pmskInsertUiEvent = insertUiStatePmsk.insertUiEventPmsk,
            onValueChange = onpemasokValueChange,
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
    pmskInsertUiEvent: PmskInsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (PmskInsertUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){

        OutlinedTextField(
            value = pmskInsertUiEvent.idPemasok,
            onValueChange = {onValueChange(pmskInsertUiEvent.copy(idPemasok = it))},
            label = { Text("id Pemasok") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = pmskInsertUiEvent.namaPemasok,
            onValueChange = {onValueChange(pmskInsertUiEvent.copy(namaPemasok = it))},
            label = { Text("Nama Pemasok") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = pmskInsertUiEvent.teleponPemasok,
            onValueChange = { newValue ->
                // Validate that the new value is numeric
                if (newValue.all { it.isDigit() }) {
                    onValueChange(pmskInsertUiEvent.copy(teleponPemasok = newValue))
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Telepon Pemasok") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = pmskInsertUiEvent.alamatPemasok,
            onValueChange = {onValueChange(pmskInsertUiEvent.copy(alamatPemasok = it))},
            label = { Text("Alamat Pemasok") },
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