package com.example.fitgame

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitgame.Screens.HomeScreen
import com.example.fitgame.Screens.MainScreen
import com.example.fitgame.Screens.Shop
import com.example.fitgame.Screens.Stats
import com.example.fitgame.backend.GameViewModel
import com.example.fitgame.backend.GameViewModelFactory
import com.example.fitgame.backend.HealthDataManager
import com.example.fitgame.backend.REQUIRED_PERMISSIONS
import com.example.fitgame.backend.Route
import com.example.fitgame.ui.theme.FitGameTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var healthDataManager: HealthDataManager
    private lateinit var gameViewModel: GameViewModel

    private val requestPermissionLauncher =
        registerForActivityResult(PermissionController.createRequestPermissionResultContract()) { granted ->

            if (granted.isNotEmpty()) {
                Log.d("FitGame", "Permissions granted successfully! Granted permissions: $granted")
                gameViewModel.fetchHealthData()
            } else {
                Log.w("FitGame", "Permissions Denied or User Cancelled.")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        healthDataManager = HealthDataManager(this)
        val factory = GameViewModelFactory(healthDataManager)
        gameViewModel = ViewModelProvider(this, factory)[GameViewModel::class.java]

        enableEdgeToEdge()

        lifecycleScope.launch {
            if (healthDataManager.hasAllPermissions()) {
                gameViewModel.fetchHealthData()
            }
        }

        setContent {
            FitGameTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.HomeScreen,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Route.MainScreen> {
                            MainScreen(
                                navController = navController,
                                viewModel = gameViewModel,
                                onConnectHealthClick = {
                                    lifecycleScope.launch {
                                        requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
                                    }
                                }
                            )
                        }
                        composable<Route.Stats> { Stats(navController, gameViewModel) }
                        composable<Route.Shop> { Shop(modifier = Modifier, navController, gameViewModel) }
                        composable<Route.HomeScreen> {
                            HomeScreen(
                                modifier = Modifier,
                                navController = navController,
                                viewModel = gameViewModel,
                                onConnectHealthClick = {
                                    lifecycleScope.launch {
                                        requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}