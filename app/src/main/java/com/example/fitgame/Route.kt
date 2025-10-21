package com.example.fitgame

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object MainScreen

    @Serializable
    data object Shop

    @Serializable
    data object Stats

}