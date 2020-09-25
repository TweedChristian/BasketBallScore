package com.tweedchristian.android.basketballscore

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "BasketballViewModel"


class BasketballViewModel: ViewModel() {
    private val game: BasketBallGame = BasketBallGame()

    val teamOneCurrentPoints: Int
        get() = this.game.getTeamOne.points

    val teamTwoCurrentPoints: Int
        get() = this.game.getTeamTwo.points

    val basketBallGame: BasketBallGame
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
        this.game.reset(this.game.getTeamOne.name, this.game.getTeamTwo.name)
        this.game.updatePoints(true, teamOnePoints)
        this.game.updatePoints(false, teamTwoPoints)
    }

    fun loadDataFromNewInstance(teamOnePoints: Int, teamTwoPoints: Int, teamOneName: String, teamTwoName: String) {
        Log.i(TAG, teamOneName)
        this.game.reset(teamOneName, teamTwoName)
        this.game.updatePoints(true, teamOnePoints)
        this.game.updatePoints(false, teamTwoPoints)
        Log.i(TAG, this.game.getTeamOne.name)
    }
}