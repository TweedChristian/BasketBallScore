package com.tweedchristian.android.basketballscore

import androidx.lifecycle.ViewModel

class BasketBallGameListViewModel : ViewModel() {
    private val basketBallGames = mutableListOf<BasketBallGame>()

    val games: List<BasketBallGame>
        get() = this.basketBallGames

    init {
        for(i in 0 until 100) {
            val game = BasketBallGame()
            game.randomInit()
            basketBallGames += game
        }
    }
}