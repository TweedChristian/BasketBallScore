package com.tweedchristian.android.basketballscore

class BasketBallTeam (teamName: String?) {
    private var currentPoints: Int = 0
    private var teamName: String = teamName ?: "Team"

    val points: Int
        get() = this.currentPoints

    val name: String
        get() = this.teamName

    fun updateTeamName(newName: String) {
        this.teamName = newName
    }

    fun updatePoints(addedPoints: Int) {
        this.currentPoints += addedPoints
    }

    fun reset(teamName: String?) {
        this.currentPoints = 0
        this.teamName = teamName ?: "Team"
    }

}