package com.example.fitgame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Stats(modifier: Modifier = Modifier,
          navController: NavController,
          viewModel: GameViewModel) {
    val steps by viewModel.totalSteps.collectAsState()
    val coins by viewModel.coins.collectAsState()
    val sleep by viewModel.hoursSleep.collectAsState()
    val tokens by viewModel.tokens.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(Color.Cyan),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Stats!!!", fontSize = 50.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(50.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Total Steps: ", fontSize = 40.sp, fontWeight = FontWeight.Bold)
            Text("$steps", fontSize = 40.sp)
        }
        Spacer( modifier = Modifier.size(30.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Coins: ", fontSize = 40.sp, fontWeight = FontWeight.Bold)
            Text("$coins", fontSize = 40.sp)
        }
        Spacer( modifier = Modifier.size(30.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Hours of Sleep: ", fontSize = 40.sp, fontWeight = FontWeight.Bold)
            Text("$sleep", fontSize = 40.sp)
        }
        Spacer( modifier = Modifier.size(30.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Tokens: ", fontSize = 40.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
            Text("$tokens", fontSize = 40.sp)
        }
        Spacer( modifier = Modifier.size(45.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {navController.popBackStack()}) {Text("Back")}
            Button(onClick = {navController.navigate(Route.Shop)}) {Text("Shop")}
        }
    }
}