package com.tweedchristian.android.basketballscore

import java.util.*
import kotlin.math.abs

class BasketBallGame {
    private val gameId: UUID = UUID.randomUUID()
    private val playedDate: Date = Date()
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

    fun randomInit() {
        val random = Random()
        teamOne.updatePoints(abs(random.nextInt() % 100))
        teamTwo.updatePoints(abs(random.nextInt() % 100))
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val randomString1: String = List(20) { alphabet.random() }.joinToString("")
        val randomString2: String = List(20) { alphabet.random() }.joinToString("")
        teamOne.updateTeamName(randomString1)
        teamTwo.updateTeamName(randomString2)
    }

    val getTeamOne: BasketBallTeam
        get() = this.teamOne

    val getTeamTwo: BasketBallTeam
        get() = this.teamTwo

    val id: UUID
        get() = this.gameId

    val date: Date
        get() = this.playedDate
}