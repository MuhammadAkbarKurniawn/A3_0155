package com.android.projectakhirpam.ui.view.kategori

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
import com.android.projectakhirpam.ui.viewmodel.kategori.KategoriUpdateViewModel
import com.android.projectakhirpam.ui.viewmodel.penyediaViewModel
import com.android.projectakhirpam.ui.viewmodel.produk.UpdateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateKategori : DestinasiNavigasi {
    override val route = "update Kategori"
    override val titleRes = "update Kategori"
    const val IDKTGR = "id_kategori"
    val routeWithArg = "$route/{$IDKTGR}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateKategoriScreen(
    onBack: () -> Unit,
    onNavigate:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: KategoriUpdateViewModel = viewModel(factory = penyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateKategori.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){ padding ->
        EntryBodyKtgr(
            modifier = Modifier.padding(padding),
            onkategoriValueChange =  viewModel::updateInsertKtgrState,
            insertUiStateKtgr = viewModel.uiStateKtgr,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateKtgr()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}