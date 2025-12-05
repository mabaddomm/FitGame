package com.example.fitgame.backend

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitgame.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import com.example.fitgame.ui.theme.PlacedItem

data class InventoryItem(
    val id: Long,
    val itemId: String,
    val name: String,
    val img: Int,
)

data class ShopItem(
    val itemId: String,
    val name: String,
    val img: Int,
    val cost: Int,
    val buildTime: Int,
    val description: String
)

class GameViewModel(private val healthDataManager: HealthDataManager) : ViewModel() {

    private val _coins = MutableStateFlow(0)
    val coins: StateFlow<Int> = _coins.asStateFlow()

    private val _tokens = MutableStateFlow(0)
    val tokens: StateFlow<Int> = _tokens.asStateFlow()

    private val _totalSteps = MutableStateFlow(0L)
    val totalSteps: StateFlow<Long> = _totalSteps.asStateFlow()

    private val _hoursSleep = MutableStateFlow(0)
    val hoursSleep: StateFlow<Int> = _hoursSleep.asStateFlow()

    private val _miles = MutableStateFlow(0.0)
    val miles: StateFlow<Double> = _miles.asStateFlow()

    private val _inventory = MutableStateFlow<List<InventoryItem>>(emptyList())
    val inventory: StateFlow<List<InventoryItem>> = _inventory.asStateFlow()

    fun fetchHealthData() {
        viewModelScope.launch {
            val now = Instant.now()
            val startOfDay = now.atZone(healthDataManager.client?.let { ZoneId.systemDefault() } ?: ZoneId.of("UTC"))
                .toLocalDate().atStartOfDay(healthDataManager.client?.let { ZoneId.systemDefault() } ?: ZoneId.of("UTC")).toInstant()
            val metrics = healthDataManager.readHealthDataMetrics(startOfDay, now)
            _totalSteps.value = metrics.steps
            _hoursSleep.value = metrics.sleepSessions
            val distanceInMiles = metrics.distance / 1609.34
            _miles.value = distanceInMiles
            calculateRewards(metrics)
        }
    }

    private fun calculateRewards(metrics: HealthDataMetrics) {

        val coinReward = (metrics.steps / 100).toInt() * 10
        val tokenRewardFromSleep = _hoursSleep.value
        val distanceInMiles = metrics.distance / 1609.34
        val tokenRewardFromMiles = distanceInMiles.toInt()
        val totalTokenReward = tokenRewardFromSleep + tokenRewardFromMiles
        _coins.update { it + coinReward }
        _tokens.update { it + totalTokenReward }

        Log.d("GameViewModel", "Rewards calculated: +$coinReward Coins, +$totalTokenReward Tokens")
        Log.d("GameViewModel", "Distance in Miles: ${String.format("%.2f", distanceInMiles)}. Tokens from Miles: $tokenRewardFromMiles.")
    }

    fun purchaseItem(item: ShopItem) {
        viewModelScope.launch {
            if (_coins.value >= item.cost) {
                _coins.update { it - item.cost }

                val uniqueInstanceId = System.currentTimeMillis()

                val newItem = InventoryItem(
                    id = uniqueInstanceId,
                    itemId = item.itemId,
                    name = item.name,
                    img = item.img
                )

                // Add to inventory
                _inventory.update { currentList ->
                    currentList + newItem
                }
                Log.d("GameViewModel", "${item.name} purchased! Coins remaining: ${_coins.value}")
            } else {
                Log.w("GameViewModel", "Purchase failed: Not enough coins for ${item.name}.")
            }
        }
    }

    private val _coordinateInput = MutableStateFlow("0,0")
    val coordinateInput: StateFlow<String> = _coordinateInput.asStateFlow()

    fun updateCoordinateInput(newInput: String) {
        _coordinateInput.value = newInput
    }

    fun parseCoordinates(input: String): Pair<Int, Int>? {
        return try {
            val parts = input.split(",").map { it.trim().toInt() }
            if (parts.size == 2 && parts[0] >= 0 && parts[1] >= 0) {
                Pair(parts[0], parts[1])
            } else {
                null
            }
        } catch (e: NumberFormatException) {
            null
        }
    }


    private val _placedItems = MutableStateFlow<List<PlacedItem>>(emptyList())
    val placedItems: StateFlow<List<PlacedItem>> = _placedItems.asStateFlow()
    fun placeItemOnMap(instanceId: Long, itemId: String, name: String, img: Int, row: Int, col: Int) {
        viewModelScope.launch {


            val itemToPlace = _inventory.value.firstOrNull { it.id == instanceId }

            if (itemToPlace == null) {
                Log.e("GameViewModel", "Attempted to place item with ID $instanceId, but it was not found in inventory.")
                return@launch
            }


            _inventory.update { currentList ->
                currentList.filter { it.id != instanceId }
            }

            val newPlacedItem = PlacedItem(
                id = instanceId,
                itemId = itemId,
                name = name,
                img = img,
                row = row,
                col = col
            )

            _placedItems.update { currentList ->
                currentList + newPlacedItem
            }

            Log.d("GameViewModel", "Item ${itemToPlace.name} (ID $instanceId) placed at ($row, $col).")
        }
    }
        val itemsInShop = listOf(
            ShopItem("1", "Trees (Grass Terrain)", R.drawable.g_extra_trees, cost = 0, buildTime = 0, description = "Adds extra trees to grass terrain."),
            ShopItem("2", "Rock + Tree (Grass Terrain)", R.drawable.g_rock_tree, cost = 0, buildTime = 0, description = "Places a rock and tree on grass terrain."),
            ShopItem("3", "Trees (Dirt Terrain)", R.drawable.d_tree, cost = 0, buildTime = 0, description = "Adds trees to dirt terrain."),
            ShopItem("4", "Rocks (Dirt Terrain)", R.drawable.d_rock, cost = 0, buildTime = 0, description = "Places rocks on dirt terrain."),
            ShopItem("5", "Rock + Tree (Dirt Terrain)", R.drawable.d_rock_tree, cost = 0, buildTime = 0, description = "Places rocks and trees on dirt terrain."),
            ShopItem("6", "Cactus (Sand Terrain)", R.drawable.s_cactus, cost = 0, buildTime = 0, description = "Adds cactus to sand terrain."),
            ShopItem("7", "Rocks (Sand Terrain)", R.drawable.s_rock, cost = 0, buildTime = 0, description = "Places rocks on sand terrain."),
            ShopItem("8", "Rocks + Cactus (Sand Terrain)", R.drawable.s_rock_cactus, cost = 0, buildTime = 0, description = "Places rocks and cactus on sand terrain."),
            ShopItem("9", "Medieval Blacksmith", R.drawable.medieval_blacksmith, cost = 20, buildTime = 0, description = "A Building To Make Weapons If you want the old style."),
            ShopItem("10", "Medieval Cabin", R.drawable.medieval_cabin, cost = 20, buildTime = 0, description = "A simple wooden cabin make fun memories here."),
            ShopItem("11", "Medieval Farm", R.drawable.medieval_farm, cost = 20, buildTime = 0, description = "A Small Simple Farm, but still good for food"),
            ShopItem("12", "Medieval House", R.drawable.medieval_house, cost = 35, buildTime = 0, description = "A Home with the Old Medieval Style If that is your thing."),
            ShopItem("13", "Medieval Tower", R.drawable.medieval_tower, cost = 10, buildTime = 0, description = "A Tower, who knows that it does tho"),
            ShopItem("14", "Medieval Windmill", R.drawable.medieval_windmill, cost = 10, buildTime = 0, description = "This is Just a wind mill nothing special :("),
            ShopItem("15", "Western Bank", R.drawable.western_bank, cost = 35, buildTime = 0, description = "A Western Style Sandy bank."),
            ShopItem("16", "Western General Store", R.drawable.western_general, cost = 35, buildTime = 0, description = "A store for goods and supplies in the Dessert."),
            ShopItem("17", "Western Saloon", R.drawable.western_saloon, cost = 20, buildTime = 0, description = "A western bar takin place in the Nice Dessert."),
            ShopItem("18", "Western Sheriff Office", R.drawable.western_sheriff, cost = 40, buildTime = 0, description = "The Sherif Office for the West."),
            ShopItem("19", "Western Train Station", R.drawable.western_station, cost = 40, buildTime = 0, description = "Train Station essential for Dessert Travel"),
            ShopItem("20", "Mill Cutter", R.drawable.mill_cutter, cost = 25, buildTime = 0, description = "Mill Cutter, to help make wood for the village."),
            ShopItem("21", "Mill Factory", R.drawable.mill_factory, cost = 30, buildTime = 0, description = "Could be a mill Factory Could be a Factory for Other Things ?!?! ."),
            ShopItem("22", "Mill Storage", R.drawable.mill_storage, cost = 35, buildTime = 0, description = "Storage building Just to keep your Stuff."),
            ShopItem("23", "Mill Warehouse", R.drawable.mill_warehouse, cost = 20, buildTime = 0, description = "Ware house Good for Work.")
        )
    }



