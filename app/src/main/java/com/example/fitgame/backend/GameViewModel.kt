package com.example.fitgame.backend

import androidx.lifecycle.ViewModel
import com.example.fitgame.R
import io.github.serpro69.kfaker.faker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.fitgame.backend.buildingsInShop

class GameViewModel: ViewModel() {

    val faker = faker {}
    val randomstep = faker.random.nextInt(3000, 10000)
    val randomsleep = faker.random.nextInt(1, 24)
    private val _totalSteps = MutableStateFlow(randomstep)
    val totalSteps = _totalSteps.asStateFlow()

    private val _coins = MutableStateFlow(randomstep / 50)
    val coins = _coins.asStateFlow()

    private val _hoursSleep = MutableStateFlow(randomsleep)
    val hoursSleep = _hoursSleep.asStateFlow()

    private val _tokens = MutableStateFlow(randomsleep * 3)
    val tokens = _tokens.asStateFlow()

    fun updateSteps(steps: Int) {
        _totalSteps.update {it + steps}
        _coins.value = _totalSteps.value / 50
    }

    fun setSteps(steps: Int){
        _totalSteps.value = steps
        _coins.value = steps / 50
    }

    fun updateSleep(hours: Int){
        _hoursSleep.update { it + hours }
        _tokens.value = _hoursSleep.value * 3
    }

    fun setSleep(hours: Int){
        _hoursSleep.value = hours
        _tokens.value = hours * 3
    }

    val itemsInShop = listOf(
        buildingsInShop("Trees (Grass Terrain)", R.drawable.g_extra_trees, cost = 0, buildTime = 0, description = "Adds extra trees to grass terrain."),
        buildingsInShop("Rock + Tree (Grass Terrain)", R.drawable.g_rock_tree, cost = 0, buildTime = 0, description = "Places a rock and tree on grass terrain."),

        buildingsInShop("Trees (Dirt Terrain)", R.drawable.d_tree, cost = 0, buildTime = 0, description = "Adds trees to dirt terrain."),
        buildingsInShop("Rocks (Dirt Terrain)", R.drawable.d_rock, cost = 0, buildTime = 0, description = "Places rocks on dirt terrain."),
        buildingsInShop("Rock + Tree (Dirt Terrain)", R.drawable.d_rock_tree, cost = 0, buildTime = 0, description = "Places rocks and trees on dirt terrain."),

        buildingsInShop("Cactus (Sand Terrain)", R.drawable.s_cactus, cost = 0, buildTime = 0, description = "Adds cactus to sand terrain."),
        buildingsInShop("Rocks (Sand Terrain)", R.drawable.s_rock, cost = 0, buildTime = 0, description = "Places rocks on sand terrain."),
        buildingsInShop("Rocks + Cactus (Sand Terrain)", R.drawable.s_rock_cactus, cost = 0, buildTime = 0, description = "Places rocks and cactus on sand terrain."),

        buildingsInShop("Medieval Blacksmith", R.drawable.medieval_blacksmith, cost = 0, buildTime = 0, description = "A Building To Make Weapons If you want the old style."),
        buildingsInShop("Medieval Cabin", R.drawable.medieval_cabin, cost = 0, buildTime = 0, description = "A simple wooden cabin make fun memories here."),
        buildingsInShop("Medieval Farm", R.drawable.medieval_farm, cost = 0, buildTime = 0, description = "A Small Simple Farm, but still good for food"),
        buildingsInShop("Medieval House", R.drawable.medieval_house, cost = 0, buildTime = 0, description = "A Home with the Old Medieval Style If that is your thing."),
        buildingsInShop("Medieval Tower", R.drawable.medieval_tower, cost = 0, buildTime = 0, description = "A Tower, who knows that it does tho"),
        buildingsInShop("Medieval Windmill", R.drawable.medieval_windmill, cost = 0, buildTime = 0, description = "This is Just a wind mill nothing special :("),

        buildingsInShop("Western Bank", R.drawable.western_bank, cost = 0, buildTime = 0, description = "A Western Style Sandy bank."),
        buildingsInShop("Western General Store", R.drawable.western_general, cost = 0, buildTime = 0, description = "A store for goods and supplies in the Dessert."),
        buildingsInShop("Western Saloon", R.drawable.western_saloon, cost = 0, buildTime = 0, description = "A western bar takin place in the Nice Dessert."),
        buildingsInShop("Western Sheriff Office", R.drawable.western_sheriff, cost = 0, buildTime = 0, description = "The Sherif Office for the West."),
        buildingsInShop("Western Train Station", R.drawable.western_station, cost = 0, buildTime = 0, description = "Train Station essential for Dessert Travel"),

        buildingsInShop("Mill Cutter", R.drawable.mill_cutter, cost = 0, buildTime = 0, description = "Mill Cutter, to help make wood for the village."),
        buildingsInShop("Mill Factory", R.drawable.mill_factory, cost = 0, buildTime = 0, description = "Could be a mill Factory Could be a Factory for Other Things ?!?! ."),
        buildingsInShop("Mill Storage", R.drawable.mill_storage, cost = 0, buildTime = 0, description = "Storage building Just to keep your Stuff."),
        buildingsInShop("Mill Warehouse", R.drawable.mill_warehouse, cost = 0, buildTime = 0, description = "Ware house Good for Work."),

    )

}