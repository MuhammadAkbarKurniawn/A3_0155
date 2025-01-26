package com.android.projectakhirpam.ui.view.pemasok

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
import com.android.projectakhirpam.ui.viewmodel.pemasok.PemasokUpdateViewModel
import com.android.projectakhirpam.ui.viewmodel.penyediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdatePemasok : DestinasiNavigasi {
    override val route = "update pemasok"
    override val titleRes = "update Pemasok"
    const val IDPMSK = "id_pemasok"
    val routeWithArg = "$route/{$IDPMSK}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePemasokScreen(
    onBack: () -> Unit,
    onNavigate:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: PemasokUpdateViewModel = viewModel(factory = penyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePemasok.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){ padding ->
        EntryBodyPmsk(
            modifier = Modifier.padding(padding),
            onpemasokValueChange = viewModel::updateInsertPmskState,
            insertUiStatePmsk = viewModel.uiStatePmsk,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePmsk()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}