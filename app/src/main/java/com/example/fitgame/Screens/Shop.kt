package com.example.fitgame.Screens


import android.R.attr.fontWeight
import android.R.attr.onClick
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
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitgame.backend.GameViewModel
import com.example.fitgame.R
import com.example.fitgame.backend.Route



@Composable
fun Shop(modifier: Modifier = Modifier,
         navController: NavController,
         viewModel: GameViewModel
)
    {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text("Game Shop", fontSize = 50.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {navController.popBackStack()}) { Text("Back") }
            Button(onClick = {navController.navigate(Route.Stats)}) { Text("Stats") }
        }
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(8.dp))
        {
            items(viewModel.itemsInShop) { item ->
                Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {

                    Column(modifier = Modifier.padding(8.dp)) {
                        Image(
                            modifier = Modifier.size(100.dp),
                            painter = painterResource(id = item.img),
                            contentDescription = null)
                        ElevatedButton( onClick = {/*TODO*/}) {
                            Text("Purchase")
                        }
                    }
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(item.name, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                        Text("Desc: ${item.description}", fontSize = 15.sp)
                        Text("Item Cost: ${item.cost} Coins\nItem Build Time: ${item.buildTime}", fontSize = 15.sp)
                    }
                }
            }
        }
    }
}



