package com.android.projectakhirpam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.projectakhirpam.ui.view.kategori.DestinasiDetailKategori
import com.android.projectakhirpam.ui.view.kategori.DestinasiEntryKategori
import com.android.projectakhirpam.ui.view.kategori.DestinasiHomeKategori
import com.android.projectakhirpam.ui.view.kategori.DestinasiUpdateKategori
import com.android.projectakhirpam.ui.view.kategori.DetailKategoriScreen
import com.android.projectakhirpam.ui.view.kategori.EntryKategoriScreen
import com.android.projectakhirpam.ui.view.kategori.HomeKategoriScreen
import com.android.projectakhirpam.ui.view.kategori.UpdateKategoriScreen
import com.android.projectakhirpam.ui.view.pemasok.DestinasiDetailPemasok
import com.android.projectakhirpam.ui.view.pemasok.DestinasiEntryPemasok
import com.android.projectakhirpam.ui.view.pemasok.DestinasiHomePemasok
import com.android.projectakhirpam.ui.view.pemasok.DestinasiUpdatePemasok
import com.android.projectakhirpam.ui.view.pemasok.DetailPemasokScreen
import com.android.projectakhirpam.ui.view.pemasok.EntryPemasokScreen
import com.android.projectakhirpam.ui.view.pemasok.HomePemasokScreen
import com.android.projectakhirpam.ui.view.pemasok.UpdatePemasokScreen
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
        // Home produk
        composable(DestinasiHome.route){
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = {idproduk ->
                    navController.navigate("${DestinasiDetail.route}/$idproduk")
                    println("PengelolaHalaman: idproduk = $idproduk")
                }
            )
        }

        // Insert produk
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

        //Detail Produk
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
                    },
                    navigateToHomePemasok = {
                        navController.navigate(DestinasiHomePemasok.route)
                    }
                )
            }
        }

        // Update Produk
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


        // Home Kategori
        composable(DestinasiHomeKategori.route) {
            HomeKategoriScreen(
                navigateBack = { navController.popBackStack() },
                onDetailClick = {idkategori ->
                    navController.navigate("${DestinasiDetailKategori.route}/$idkategori")
                    println("PengelolaHalaman: idkategori = $idkategori")
                },
                navigateToKategoriEntry = {
                    navController.navigate(DestinasiEntryKategori.route)
                }
            )
        }

        // Insert Kaategori
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

        // Detail Kategori
        composable (
            DestinasiDetailKategori.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailKategori.IDKTGR) {
                    type = NavType.StringType
                }
            )
        ){
            val idkategori = it.arguments?.getString(DestinasiDetailKategori.IDKTGR)

            idkategori?.let { idkategori ->
                DetailKategoriScreen(
                    navigateBack = {
                        navController.navigate(DestinasiHomeKategori.route) {
                            popUpTo(DestinasiHomeKategori.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToItemUpdate = {
                        navController.navigate("${DestinasiUpdateKategori.route}/$idkategori")
                    }
                )
            }
        }

        // Update kategori
        composable(
            DestinasiUpdateKategori.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailKategori.IDKTGR){
                    type = NavType.StringType
                }
            )
        ){
            val idkategori = it.arguments?.getString(DestinasiUpdateKategori.IDKTGR)
            idkategori?.let { id ->
                UpdateKategoriScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // Home Pemasok
        composable(DestinasiHomePemasok.route) {
            HomePemasokScreen(
                navigateBack = { navController.popBackStack() },
                onDetailClick = {idpemasok ->
                    navController.navigate("${DestinasiDetailPemasok.route}/$idpemasok")
                    println("PengelolaHalaman: idpemasok = $idpemasok")
                },
                navigateToKategoriEntry = {
                    navController.navigate(DestinasiEntryPemasok.route)
                }
            )
        }

        // Insert Pemasok
        composable(DestinasiEntryPemasok.route) {
            EntryPemasokScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomePemasok.route){
                        popUpTo(DestinasiHomePemasok.route){
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Detail Pemasok
        composable (
            DestinasiDetailPemasok.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPemasok.IDPMSK) {
                    type = NavType.StringType
                }
            )
        ){
            val idpemasok = it.arguments?.getString(DestinasiDetailPemasok.IDPMSK)

            idpemasok?.let { idpemasok ->
                DetailPemasokScreen(
                    navigateBack = {
                        navController.navigate(DestinasiHomePemasok.route) {
                            popUpTo(DestinasiHomePemasok.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToItemUpdate = {
                        navController.navigate("${DestinasiUpdatePemasok.route}/$idpemasok")
                    }
                )
            }
        }

        // Update Pemasok
        composable(
            DestinasiUpdatePemasok.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPemasok.IDPMSK){
                    type = NavType.StringType
                }
            )
        ){
            val idpemasok = it.arguments?.getString(DestinasiUpdatePemasok.IDPMSK)
            idpemasok?.let { id ->
                UpdatePemasokScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.popBackStack()
                    }
                )
            }
        }

    }
}