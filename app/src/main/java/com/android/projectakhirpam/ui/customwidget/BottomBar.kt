package com.android.projectakhirpam.ui.customwidget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.projectakhirpam.ui.view.kategori.DestinasiHomeKategori
import com.android.projectakhirpam.ui.view.merk.DestinasiHomeMerk
import com.android.projectakhirpam.ui.view.pemasok.DestinasiHomePemasok
import com.android.projectakhirpam.ui.view.produk.DestinasiHome

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        val items = listOf(
            DestinasiHome,
            DestinasiHomeKategori,
            DestinasiHomePemasok,
            DestinasiHomeMerk
        )

        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination

        items.forEachIndexed { index, destination ->
            NavigationBarItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))
                        when (destination) {
                            DestinasiHome -> Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "Home",
                                tint = if (currentDestination?.route == destination.route) Color.Blue else Color.DarkGray
                            )
                            DestinasiHomeKategori -> Icon(
                                imageVector = Icons.Filled.List,
                                contentDescription = "Kategori",
                                tint = if (currentDestination?.route == destination.route) Color.Blue else Color.DarkGray
                            )
                            DestinasiHomePemasok -> Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Pemasok",
                                tint = if (currentDestination?.route == destination.route) Color.Blue else Color.DarkGray
                            )
                            DestinasiHomeMerk -> Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Merk",
                                tint = if (currentDestination?.route == destination.route) Color.Blue else Color.DarkGray
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = when (destination) {
                                DestinasiHome -> "Produk"
                                DestinasiHomeKategori -> "Kategori"
                                DestinasiHomePemasok -> "Pemasok"
                                DestinasiHomeMerk -> "Merk"
                                else -> ""
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = if (currentDestination?.route == destination.route) Color.Blue else Color.DarkGray
                        )
                    }
                },
                selected = currentDestination?.route == destination.route,
                onClick = {
                    if (currentDestination?.route != destination.route) {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            )
        }
    }
}
