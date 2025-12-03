package com.example.fitgame.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.fitgame.R

// ----------------------------
// DATA MODELS
// ----------------------------
enum class TerrainType {
    GRASS, DIRT, SAND, STONE
}

data class TerrainTile(
    val row: Int,
    val col: Int,
    val type: TerrainType
)

data class GameMap(
    val rows: Int,
    val cols: Int,
    val tiles: List<TerrainTile>
)

// ----------------------------
// HELPER: Make your fixed 8-row map
// ----------------------------
fun createDefaultTerrainMap(): GameMap {
    val rows = 9      // 2 grass + 2 dirt + 2 sand + 2 stone
    val basecols = 12     // wide map; change later

    val tiles = mutableListOf<TerrainTile>()

    repeat(rows) { r ->
        val type = when {
            r < 3 -> TerrainType.GRASS
            r < 6 -> TerrainType.DIRT
            else -> TerrainType.SAND
        }

        val colsinRow = if ( r % 2 == 1) basecols + 1 else basecols

        repeat(colsinRow) { c ->
            tiles += TerrainTile(row = r, col = c, type = type)
        }
    }

    return GameMap(rows, basecols + 1, tiles)
}

@Composable
fun TerrainMap(
    gameMap: GameMap,
    tileSize: Dp = 54.dp
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        for (r in 0 until gameMap.rows) {

            // stagger offset for hex map
            val offsetX = if (r % 2 == 0) 0.dp else tileSize / 2

            // background color for the row
            val rowColor = when (r) {
                0,1,2 -> colorResource(id = R.color.grass_green) // grass placeholder
                3,4,5 -> colorResource(id = R.color.dirt_brown) // dirt
                else -> colorResource(id = R.color.sand_color)// sand
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(rowColor)
                    .offset(x = offsetX)

            ) {
                val rowTiles = gameMap.tiles.filter { it.row == r }

                rowTiles.forEach { tile ->
                    val res = when (tile.type) {
                        TerrainType.GRASS -> R.drawable.plain_grass
                        TerrainType.DIRT -> R.drawable.plain_dirt
                        TerrainType.SAND -> R.drawable.plain_sand
                        TerrainType.STONE -> R.drawable.plain_stone
                    }

                    Image(
                        painter = painterResource(id = res),
                        contentDescription = tile.type.name,
                        modifier = Modifier.size(tileSize)
                    )
                }
            }
        }
    }
}
