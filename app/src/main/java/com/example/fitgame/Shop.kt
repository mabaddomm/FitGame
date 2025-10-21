package com.example.fitgame


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun Shop(modifier: Modifier = Modifier,
         navController: NavController,
         viewModel: GameViewModel) {


    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text("Game Shop", fontSize = 50.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(50.dp))
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            /*item #1*/
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(id = R.drawable.temphouses),
                    contentDescription = null
                )
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("Cost of | Group of Houses: ")
                    Text("Number of Item in Your Inventory: ")
                }
            }
            /*item #2*/
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(id = R.drawable.temphouses),
                    contentDescription = null
                )
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("Cost of | Group of Houses 2: ")
                    Text("Number of Item in Your Inventory: ")
                }
            }
            /*item #3*/
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(id = R.drawable.temphouses),
                    contentDescription = null
                )
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("Cost of | Group of Houses 3: ")
                    Text("Number of Item in Your Inventory: ")
                }
            }

        }

        Spacer(modifier = Modifier.size(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {navController.popBackStack()}) { Text("Back") }
            Button(onClick = {navController.navigate(Route.Stats)}) { Text("Stats") }
        }
    }
            }



