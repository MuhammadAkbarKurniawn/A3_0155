package com.android.projectakhirpam.ui.view.merk

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.projectakhirpam.ui.customwidget.CostumeTopAppBar
import com.android.projectakhirpam.ui.navigation.DestinasiNavigasi
import com.android.projectakhirpam.ui.view.produk.DestinasiUpdate
import com.android.projectakhirpam.ui.viewmodel.merk.MerkUpdateViewModel
import com.android.projectakhirpam.ui.viewmodel.pemasok.PemasokUpdateViewModel
import com.android.projectakhirpam.ui.viewmodel.penyediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateMerk : DestinasiNavigasi {
    override val route = "update merk"
    override val titleRes = "update Merk"
    const val IDMERK = "id_merk"
    val routeWithArg = "$route/{$IDMERK}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateMerkScreen(
    onBack: () -> Unit,
    onNavigate:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: MerkUpdateViewModel = viewModel(factory = penyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateMerk.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){ padding ->
        EntryBodyMerk(
            modifier = Modifier.padding(padding),
            onMerkValueChange = viewModel::updateInsertMerkState,
            insertUiStateMerk = viewModel.uiStateMerk,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateMerk()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}