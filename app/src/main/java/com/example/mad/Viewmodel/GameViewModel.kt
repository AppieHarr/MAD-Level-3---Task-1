package com.example.mad.Viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    // Randomly chosen game from a list of games.
    val randomlyChosenGame = mutableStateOf(String())

    // Rating provided by the user.
    // Initial value is 5 star rating (5 floating)
    var gameRatingAccordingToUser = mutableStateOf(5f)

    fun randomAssessableGame() {
        randomlyChosenGame.value = listOf(
            "Red Dead Redemption 2", "Rocket League", "Shadow of the Tombraider"
        ).random()
    }

    fun reset() {
        randomlyChosenGame.value = String()
        gameRatingAccordingToUser.value = 5f
    }
}