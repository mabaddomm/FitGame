package com.example.fitgame

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/*The "VILLAGE" will be using images however I haven't found quality ones for
what I want with the app so currently only one is loaded just to see how the grid
system of the village would look like*/
@Composable
fun MainScreen(modifier: Modifier = Modifier,
               navController: NavController,
               viewModel: GameViewModel) {
    val coins by viewModel.coins.collectAsState()
    val tokens by viewModel.tokens.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(Color.Green),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(20.dp))
        Row(modifier = Modifier.fillMaxWidth().background(Color.Cyan),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(" Coins: $coins", fontSize = 25.sp)
            Text("Tokens: $tokens", fontSize = 25.sp)
        }
        Text("Your Village :)", fontSize = 18.sp, modifier = Modifier.padding(
            start = 16.dp))
        Spacer(modifier= Modifier.size(45.dp))
        villageLayout()
        Spacer(modifier = Modifier.size(35.dp))

        Row(modifier = modifier.fillMaxWidth().padding(30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly, ) {
            Button(onClick = {navController.navigate(Route.Stats)}) {Text("Game Stats") }
            Button(onClick = {navController.navigate(Route.Shop)}) {Text("Game Shop") }
        }
    }
}

@Composable
fun villageLayout() {

    val grid = remember { mutableListOf<String?>(
        "TempH", null, null, null,
        null, "TempH", null, null,
        null, null, "TempH", "TempH",
        null, null, null, "TempH")}

    LazyVerticalGrid(columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxWidth().padding(10.dp)) {
        items(grid.size) { index ->
            Box(modifier = Modifier.size(80.dp).padding(4.dp)) {
                grid[index]?.let {
                    buildingType ->
                    val imageRes = when (buildingType) {
                        "TempH" -> R.drawable.temphouses
                        else -> null
                    }
                    imageRes?.let{
                        Image(
                            painter = painterResource(id = it),
                            contentDescription = buildingType,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}