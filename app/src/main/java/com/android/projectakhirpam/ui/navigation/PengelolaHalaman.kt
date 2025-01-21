package com.android.projectakhirpam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.projectakhirpam.ui.view.kategori.DestinasiEntryKategori
import com.android.projectakhirpam.ui.view.kategori.DestinasiHomeKategori
import com.android.projectakhirpam.ui.view.kategori.EntryKategoriScreen
import com.android.projectakhirpam.ui.view.kategori.HomeKategoriScreen
import com.android.projectakhirpam.ui.view.produk.DestinasiDetail
import com.android.projectakhirpam.ui.view.produk.DestinasiEntry
import com.android.projectakhirpam.ui.view.produk.DestinasiHome
import com.android.projectakhirpam.ui.view.produk.DestinasiUpdate
import com.android.projectakhirpam.ui.view.produk.DetailScreen
import com.android.projectakhirpam.ui.view.produk.EntryProdukScreen
import com.android.projectakhirpam.ui.view.produk.HomeScreen
import com.android.projectakhirpam.ui.view.produk.UpdateScreen

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier,
    ){
        composable(DestinasiHome.route){
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = {idproduk ->
                    navController.navigate("${DestinasiDetail.route}/$idproduk")
                    println("PengelolaHalaman: idproduk = $idproduk")
                }
            )
        }

        composable(DestinasiEntry.route) {
            EntryProdukScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route){
                        popUpTo(DestinasiHome.route){
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable (
            DestinasiDetail.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.IDPRODUK) {
                    type = NavType.StringType
                }
            )
        ){
            val idproduk = it.arguments?.getString(DestinasiDetail.IDPRODUK)

            idproduk?.let { idproduk ->
                DetailScreen(
                    navigateBack = {
                        navController.navigate(DestinasiHome.route) {
                            popUpTo(DestinasiHome.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToItemUpdate = {
                        navController.navigate("${DestinasiUpdate.route}/$idproduk")
                    },
                    navigateToHomeKategori = {
                        navController.navigate(DestinasiHomeKategori.route)
                    }
                )
            }
        }

        composable(
            DestinasiUpdate.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.IDPRODUK){
                    type = NavType.StringType
                }
            )
        ){
            val idproduk = it.arguments?.getString(DestinasiUpdate.IDPRODUK)
            idproduk?.let { id ->
                UpdateScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.popBackStack()
                    }
                )
            }
        }


        composable(DestinasiHomeKategori.route) {
            HomeKategoriScreen(
                navigateBack = { navController.popBackStack() },
                navigateToKategoriEntry = {
                    navController.navigate(DestinasiEntryKategori.route)
                }
            )
        }
        composable(DestinasiEntryKategori.route) {
            EntryKategoriScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeKategori.route){
                        popUpTo(DestinasiHomeKategori.route){
                            inclusive = true
                        }
                    }
                }
            )
        }

    }
}