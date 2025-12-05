package com.example.fitgame.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitgame.R
import com.example.fitgame.backend.GameViewModel
import com.example.fitgame.backend.Route

@Composable
fun Stats(navController: NavController, viewModel: GameViewModel) {
    val steps by viewModel.totalSteps.collectAsState()
    val coins by viewModel.coins.collectAsState()
    val miles by viewModel.miles.collectAsState()
    val sleepHours by viewModel.hoursSleep.collectAsState()
    val tokens by viewModel.tokens.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().background(colorResource( id= R.color.calm_cyan)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Title
        Box(modifier = Modifier.background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(5.dp, colorResource( id= R.color.dark_cyan), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = "Health and Game Stats", fontSize = 35.sp, fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center, maxLines = 2,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        // Back & Shop buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.popBackStack() }) { Text("Back") }
            Button(onClick = { navController.navigate(Route.Shop) }) { Text("Shop") }
            Button(onClick = { navController.navigate(Route.HomeScreen) }) { Text("Home") }
        }
        Spacer(modifier = Modifier.height(30.dp))

        // 2×2 GRID
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            // 1. Steps
            item {
                Card(colors = CardDefaults.cardColors(Color.White),
                    modifier = Modifier.border(width = 5.dp, color = colorResource(id=R.color.calm_orange), shape = RoundedCornerShape(8.dp) ))
                {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
                        Icon(Icons.Default.DirectionsWalk, contentDescription = "Steps", modifier = Modifier.size(70.dp), tint = Color.Black)
                        Spacer(Modifier.height(12.dp))
                        Text("$steps", fontSize = 38.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        Text("Steps", color = Color.Black.copy(alpha = 0.8f))
                    }
                }
            }

            // 2. Coins
            item {
                Card(colors = CardDefaults.cardColors(Color.White),
                    modifier = Modifier.border(width = 5.dp, color = colorResource(id=R.color.calm_yellow), shape = RoundedCornerShape(8.dp) ))
                {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
                        Icon(Icons.Default.MonetizationOn, contentDescription = "Coins", modifier = Modifier.size(70.dp), tint = Color.Black)
                        Spacer(Modifier.height(12.dp))
                        Text("$coins", fontSize = 38.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
                        Text("Coins", color = Color.Black.copy(alpha = 0.8f))
                    }
                }
            }

            // 3. Miles
            item {
                Card(colors = CardDefaults.cardColors(Color.White),
                    modifier = Modifier.border(width = 5.dp, color = colorResource(id=R.color.calm_purple), shape = RoundedCornerShape(8.dp) ))
                {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
                        Icon(Icons.Default.RunCircle, contentDescription = "Miles", modifier = Modifier.size(70.dp), tint = Color.Black)
                        Spacer(Modifier.height(12.dp))
                        Text(String.format("%.1f", miles), fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        Text("Miles", color = Color.Black.copy(alpha = 0.8f))
                    }
                }
            }

            // 4. Sleep
            item {
                Card(colors = CardDefaults.cardColors(Color.White),
                    modifier = Modifier.border(width = 5.dp, color = colorResource(id=R.color.dark_purple), shape = RoundedCornerShape(8.dp) ))
                {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
                        Icon(Icons.Default.Snooze, contentDescription = "Sleep", modifier = Modifier.size(70.dp), tint = Color.Black)
                        Spacer(Modifier.height(12.dp))
                        Text("$sleepHours", fontSize = 38.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        Text("Sleep (hrs)", color = Color.Black.copy(alpha = 0.8f))
                    }
                }
            }
        }

        // Tokens
        Card(
            modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
                .border(width = 5.dp, color = colorResource(id = R.color.dark_orange), shape = RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(Icons.Default.Star, contentDescription = "Tokens", modifier = Modifier.size(48.dp), tint = Color.Black)
                Spacer(Modifier.width(16.dp))
                Text("$tokens Tokens", fontSize = 34.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
            }
        }
        Spacer(Modifier.height(32.dp))
    }
}