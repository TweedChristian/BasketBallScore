package com.tweedchristian.android.basketballscore

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "BasketballViewModel"

class BasketballViewModel: ViewModel() {
    private val game: Game = Game()

    val teamOneCurrentPoints: Int
        get() = this.game.teamAScore

    val teamTwoCurrentPoints: Int
        get() = this.game.teamBScore

    val basketBallGame: Game
        get() = this.game

    val winningTeam: Char
        get() = this.game.winningTeam

    fun resetPoints() {
        this.game.reset(null, null)
    }

    fun updatePoints(teamOne: Boolean, pointAmount: Int){
        if(teamOne){
            this.game.updatePoints(true, pointAmount)
        }
        else {
            this.game.updatePoints(false, pointAmount)
        }
    }

    fun setPointsFromSavedState(teamOnePoints: Int, teamTwoPoints: Int) {
        this.game.reset(this.game.teamAName, this.game.teamBName)
        this.game.updatePoints(true, teamOnePoints)
        this.game.updatePoints(false, teamTwoPoints)
    }

    fun loadDataFromNewInstance(teamOnePoints: Int, teamTwoPoints: Int, teamOneName: String, teamTwoName: String) {
        this.game.reset(teamOneName, teamTwoName)
        this.game.updatePoints(true, teamOnePoints)
        this.game.updatePoints(false, teamTwoPoints)
    }
}