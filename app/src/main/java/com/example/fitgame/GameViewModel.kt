package com.example.fitgame

import androidx.lifecycle.ViewModel
import io.github.serpro69.kfaker.faker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel: ViewModel() {

    val faker = faker{}
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

}