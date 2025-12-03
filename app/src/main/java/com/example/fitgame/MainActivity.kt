package com.example.fitgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.fitgame.ui.theme.FitGameTheme
import androidx.navigation.compose.rememberNavController
import com.example.fitgame.Screens.MainScreen
import com.example.fitgame.Screens.Shop
import com.example.fitgame.Screens.Stats
import com.example.fitgame.backend.GameViewModel
import com.example.fitgame.backend.Route

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitGameTheme {
                val navController = rememberNavController()
                val gameVm: GameViewModel = viewModel()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.MainScreen,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Route.MainScreen> {
                            MainScreen(navController = navController, viewModel = gameVm)
                        }

                        composable<Route.Stats> {
                            Stats(navController = navController, viewModel = gameVm)
                        }

                        composable<Route.Shop> {
                            Shop(navController = navController, viewModel = gameVm)
                        }
                    }
                }
            }
        }
    }
}
