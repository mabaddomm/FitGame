
package com.example.fitgame.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitgame.R
import com.example.fitgame.backend.GameViewModel
import com.example.fitgame.backend.Route
import com.example.fitgame.ui.theme.TerrainMap
import com.example.fitgame.ui.theme.createDefaultTerrainMap

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: GameViewModel
) {
    val coins by viewModel.coins.collectAsState()
    val tokens by viewModel.tokens.collectAsState()

    // Create the map once
    val terrainMap = remember { createDefaultTerrainMap() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(20.dp))

        // ----- Top Stats Bar -----
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(" Coins: $coins", fontSize = 25.sp)
            Text("Tokens: $tokens", fontSize = 25.sp)
        }

        Text("Your Village :)", fontSize = 18.sp, modifier = Modifier.padding(start = 16.dp))
        Spacer(modifier = Modifier.size(20.dp))

        // ----- TERRAIN MAP -----
        Column(modifier = Modifier.fillMaxWidth().border(width=5.dp, color = Color.Black, shape = RoundedCornerShape(5.dp))) {
        TerrainMap(
            gameMap = terrainMap,
            tileSize = 48.dp
        )}
        Spacer(modifier = Modifier.size(20.dp))

        // ----- Bottom Buttons -----
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.navigate(Route.Stats) }) {
                Text("Game Stats")
            }
            Button(onClick = { navController.navigate(Route.Shop) }) {
                Text("Game Shop")
            }
        }
    }
}
