package com.tweedchristian.android.basketballscore

class BasketBallGame {
    private val teamOne: BasketBallTeam = BasketBallTeam("Team A")
    private val teamTwo: BasketBallTeam = BasketBallTeam("Team B")

    fun updatePoints(teamOne: Boolean, pointAmount: Int) {
        if(teamOne) {
            this.teamOne.updatePoints(pointAmount)
        }
        else {
            this.teamTwo.updatePoints(pointAmount)
        }
    }

    fun reset(teamOneName: String?, teamTwoName: String?) {
        this.teamOne.reset(teamOneName ?: "Team A")
        this.teamTwo.reset(teamTwoName ?: "Team B")
    }

    fun updateTeamName(teamOne: Boolean, newTeamName: String) {
        if(teamOne) {
            this.teamOne.updateTeamName(newTeamName)
        }
        else {
            this.teamTwo.updateTeamName(newTeamName)
        }
    }

    val getTeamOne: BasketBallTeam
        get() = this.teamOne

    val getTeamTwo: BasketBallTeam
        get() = this.teamTwo
}