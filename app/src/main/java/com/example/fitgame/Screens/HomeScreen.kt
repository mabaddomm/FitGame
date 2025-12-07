package com.example.fitgame.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitgame.R
import com.example.fitgame.backend.GameViewModel
import com.example.fitgame.backend.Route
import com.example.fitgame.backend.launchGoogleFit

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: GameViewModel, onConnectHealthClick: () -> Unit)
{
    val coins by viewModel.coins.collectAsState()
    val tokens by viewModel.tokens.collectAsState()
    val isConnected = coins > 0 || tokens > 0
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.calm_cyan))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .border(5.dp, colorResource(id = R.color.dark_cyan), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally
                ) {
                    Text(
                        text = "Welcome To FitVillageGame",
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            lineHeight = 38.sp),
                        color = colorResource(id = R.color.dark_cyan),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Image(painter = painterResource(id = R.drawable.medieval_windmill), contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = if (isConnected) "Welcome back, Builder!" else "Sync your steps to start your village!",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp),
                color = colorResource(id = R.color.dark_cyan)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp, start = 30.dp, end = 30.dp), // Add horizontal padding for looks
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { launchGoogleFit(context)},
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {Text("Connect Google Fit First")}

            Button(
                onClick = onConnectHealthClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Text("Connect Health Data", textAlign = TextAlign.Center)
            }


            Button(
                onClick = { navController.navigate(Route.MainScreen) },
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) { Text("Enter Your Village", textAlign = TextAlign.Center) }
        }
    }
}