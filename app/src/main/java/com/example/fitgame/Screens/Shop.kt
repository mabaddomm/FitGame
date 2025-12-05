package com.example.fitgame.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitgame.R
import com.example.fitgame.backend.GameViewModel
import com.example.fitgame.backend.Route

@Composable
fun Shop(modifier: Modifier = Modifier, navController: NavController, viewModel: GameViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(colorResource( id= R.color.calm_cyan)),
        horizontalAlignment = Alignment.CenterHorizontally)
    {   Spacer(modifier = Modifier.size(10.dp))
        Box(modifier = Modifier.background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(5.dp, colorResource( id= R.color.dark_cyan), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp))
        {
            Text("Game Shop", fontSize = 50.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.navigate(Route.MainScreen) }) { Text("Village") }
            Button(onClick = { navController.navigate(Route.Shop) }) { Text("Shop") }
            Button(onClick = { navController.navigate(Route.HomeScreen) }) { Text("Home") }
        }
        //All The Items In The Store
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(8.dp))
        {
            items(viewModel.itemsInShop) { item ->
                Box(modifier = Modifier.background(Color.White, shape = RoundedCornerShape(8.dp))
                    .border(5.dp, colorResource( id= R.color.dark_cyan), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {

                        Column(modifier = Modifier.padding(8.dp)) {
                            Image(
                                modifier = Modifier.size(100.dp),
                                painter = painterResource(id = item.img),
                                contentDescription = null
                            )
                            ElevatedButton(onClick = { viewModel.purchaseItem(item) }) {
                                Text("Purchase")
                            }
                        }
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(item.name, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                            Text("Desc: ${item.description}", fontSize = 15.sp)
                            Text(
                                "Item Cost: ${item.cost} Coins\nItem Build Time: ${item.buildTime}",
                                fontSize = 15.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}



