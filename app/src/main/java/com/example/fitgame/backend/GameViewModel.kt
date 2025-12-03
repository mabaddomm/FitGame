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
        buildingsInShop("Trees with Green Terrain", R.drawable.g_extra_trees, cost = 0, buildTime = 0),
        buildingsInShop("Rocks and Trees with Green Terrain", R.drawable.g_rock_tree, cost = 0, buildTime = 0),
        buildingsInShop("Trees with Dirt Terrain", R.drawable.d_tree, cost = 0, buildTime = 0 ),
        buildingsInShop("Rocks with Dirt Terrain", R.drawable.d_rock, cost = 0, buildTime = 0 ),
        buildingsInShop("Rocks and Trees with Dirt Terrain", R.drawable.d_rock_tree, cost = 0, buildTime = 0),
        buildingsInShop("Cactus with Sand Terrain", R.drawable.s_cactus, cost = 0, buildTime = 0 ),
        buildingsInShop("Rocks with Sand Terrain", R.drawable.s_rock, cost = 0, buildTime = 0 ),
        buildingsInShop("Cactus and Rocks with Sand Terrain", R.drawable.s_rock_cactus, cost = 0, buildTime = 0 ),
    )
}