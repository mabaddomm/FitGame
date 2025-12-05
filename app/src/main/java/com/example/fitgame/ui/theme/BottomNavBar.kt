package com.example.fitgame.ui.theme



import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.fitgame.backend.Route
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Create

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar(containerColor = Color(0xFFF5FAFA)) {
        // Get the current route as a string
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // 2. Now access the value and chain the properties
        val currentRoute = navBackStackEntry?.destination?.route
        val items = listOf(
            Triple("HomeScreen", "Home", Icons.Filled.Home),
            Triple("Stats", "Stats", Icons.Filled.Favorite),
            Triple("MainScreen", "Village", Icons.Filled.Create),
            Triple("Shop", "Shop", Icons.Filled.Star)
        )

        items.forEach { (route, label, icon) ->
            NavigationBarItem(
                // Check if the current route string matches the item's route string
                selected = currentRoute == route,
                onClick = {
                    // Use the string navigate function
                    navController.navigate(route) {
                        // These back stack controls work with string or type-safe navigation
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                alwaysShowLabel = true
            )
        }
    }
}