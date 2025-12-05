package com.example.fitgame.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    viewModel: GameViewModel,
    onConnectHealthClick: () -> Unit)
{
    val coins by viewModel.coins.collectAsState()
    val tokens by viewModel.tokens.collectAsState()
    val inventoryItems by viewModel.inventory.collectAsState()
    val placedItems by viewModel.placedItems.collectAsState()
    val terrainMap = remember { createDefaultTerrainMap() }
    val coordinateInput by viewModel.coordinateInput.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().background(colorResource( id= R.color.calm_cyan)),
        horizontalAlignment = Alignment.CenterHorizontally
    )
        {
        Spacer(modifier = Modifier.size(10.dp))

            Box(modifier = Modifier.background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(5.dp, colorResource( id= R.color.dark_cyan), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "Your Village", fontSize = 35.sp, fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center, maxLines = 2,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                )
            }

        // Permissions button original location
//        if (coins == 0 && tokens == 0) {
//            Button(
//                onClick = onConnectHealthClick,
//                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
//            ) {
//                Text("CONNECT HEALTH DATA")
//            }
//        }


        Spacer(modifier = Modifier.size(10.dp))
        Text("Build The Village", fontSize = 18.sp, modifier = Modifier.padding(start = 16.dp))

        //Map
        Column(modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 8.dp)
            .border(width=5.dp, color = colorResource( id= R.color.dark_brown), shape = RoundedCornerShape(5.dp))) {

            Box(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).horizontalScroll(rememberScrollState())) {
                TerrainMap(
                    gameMap = terrainMap,
                    tileSize = 48.dp,
                    placedItems = placedItems
                )
            }
        }
            Spacer(modifier = Modifier.size(15.dp))

            //Inventory
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth().height(130.dp).background(Color.White, shape = RoundedCornerShape(8.dp))
                    .border(width = 4.dp, color = colorResource(id = R.color.dark_brown), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(inventoryItems) { item ->
                    Column(modifier = Modifier.background(colorResource(id = R.color.calm_cyan), shape = RoundedCornerShape(6.dp))
                            .border(width = 2.dp, color = colorResource(id = R.color.calm_brown), shape = RoundedCornerShape(6.dp))
                            .padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painter = painterResource(id = item.img), contentDescription = item.name,
                            modifier = Modifier.size(60.dp)
                        )
                        Text(item.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.Black
                        )
                        Button(
                            onClick = {
                                val coords = viewModel.parseCoordinates(coordinateInput)
                                if (coords != null) {
                                    val (row, col) = coords
                                    viewModel.placeItemOnMap(
                                        instanceId = item.id,
                                        itemId = item.itemId,
                                        name = item.name,
                                        img = item.img,
                                        row = row,
                                        col = col
                                    )
                                    viewModel.updateCoordinateInput("${row + 1},${col}")
                                }
                            },
                            modifier = Modifier.height(28.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text("Place", fontSize = 12.sp)
                        }
                    }
                }
            }

            Text("Inventory (Type Row,Col and Press Place)", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, modifier = Modifier.padding(top = 8.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = coordinateInput,
                    onValueChange = { newValue -> viewModel.updateCoordinateInput(newValue) },
                    label = { Text("Row, Col", fontSize = 10.sp) },
                    modifier = Modifier.width(150.dp).height(60.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                )
            }

        Spacer(modifier = Modifier.size(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.navigate(Route.Stats) }) { Text("Stats") }
            Button(onClick = { navController.navigate(Route.Shop) }) { Text("Shop") }
            Button(onClick = { navController.navigate(Route.HomeScreen) }) { Text("Home") }

        }
    }
}
