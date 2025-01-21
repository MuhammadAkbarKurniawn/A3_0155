package com.android.projectakhirpam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.projectakhirpam.ui.view.produk.DestinasiDetail
import com.android.projectakhirpam.ui.view.produk.DestinasiEntry
import com.android.projectakhirpam.ui.view.produk.DestinasiHome
import com.android.projectakhirpam.ui.view.produk.EntryProdukScreen
import com.android.projectakhirpam.ui.view.produk.HomeScreen

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
    }
}