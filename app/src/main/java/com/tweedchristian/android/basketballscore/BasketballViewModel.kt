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

    fun resetPoints() {
     //   Log.i(TAG, "Resetting Points")
        this.game.reset(null, null)
    }

    fun updatePoints(teamOne: Boolean, pointAmount: Int){
        if(teamOne){
            this.game.updatePoints(true, pointAmount)
    //        Log.i(TAG, "Added Team One Points: $pointAmount")
        }
        else {
            this.game.updatePoints(false, pointAmount)
     //       Log.i(TAG, "Added Team Two Points: $pointAmount")
        }
    }

    fun setPointsFromSavedState(teamOnePoints: Int, teamTwoPoints: Int) {
    //    Log.i(TAG, "Loading in team points: $teamOnePoints $teamTwoPoints")
        this.game.reset(this.game.getTeamOne.name, this.game.getTeamTwo.name)
        this.game.updatePoints(true, teamOnePoints)
        this.game.updatePoints(false, teamTwoPoints)
    }

}