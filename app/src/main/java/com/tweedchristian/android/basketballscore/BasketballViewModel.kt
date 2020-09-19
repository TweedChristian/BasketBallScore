package com.tweedchristian.android.basketballscore

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "BasketballViewModel"

class BasketballViewModel: ViewModel() {
    private var teamOnePoints: Int = 0
    private var teamTwoPoints: Int = 0

    val teamOneCurrentPoints: Int
        get() = this.teamOnePoints

    val teamTwoCurrentPoints: Int
        get() = this.teamTwoPoints

    fun resetPoints() {
     //   Log.i(TAG, "Resetting Points")
        this.teamOnePoints = 0
        this.teamTwoPoints = 0
    }

    fun updatePoints(teamOne: Boolean, pointAmount: Int){
        if(teamOne){
            this.teamOnePoints += pointAmount
    //        Log.i(TAG, "Added Team One Points: $pointAmount")
        }
        else {
            this.teamTwoPoints += pointAmount
     //       Log.i(TAG, "Added Team Two Points: $pointAmount")
        }
    }

    fun setPointsFromSavedState(teamOnePoints: Int, teamTwoPoints: Int) {
    //    Log.i(TAG, "Loading in team points: $teamOnePoints $teamTwoPoints")
        this.teamOnePoints = teamOnePoints
        this.teamTwoPoints = teamTwoPoints
    }

}