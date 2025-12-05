package com.example.fitgame.backend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameViewModelFactory(
    private val healthDataManager: HealthDataManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Checks if the requested class is the GameViewModel
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            // If it is, create and return a new instance, injecting the HealthDataManager
            return GameViewModel(healthDataManager) as T
        }
        // Otherwise, throw an exception
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}