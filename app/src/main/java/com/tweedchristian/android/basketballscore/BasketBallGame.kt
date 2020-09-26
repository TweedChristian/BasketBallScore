package com.tweedchristian.android.basketballscore

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.math.abs

const val TEAM_A_WINNING = 'A'
const val TEAM_B_WINNING = 'B'
const val TIE= 'T'

@Entity (tableName = "table_game")
data class Game(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var teamAName: String = "Team One",
    var teamBName: String = "Team Two",
    var teamAScore: Int = 0,
    var teamBScore: Int = 0,
    var date: Date = Date()
    ) {
    fun updatePoints(teamA: Boolean, pointAmount: Int) {
        if(teamA) {
            this.teamAScore += pointAmount
        }
        else {
            this.teamBScore += pointAmount
        }
    }

    fun reset(teamAName: String?, teamBName: String?) {
        this.teamAScore = 0
        this.teamBScore = 0
        this.teamAName = teamAName ?: "Team A"
        this.teamBName = teamBName ?: "Team B"
    }

    fun updateTeamName(teamA: Boolean, newTeamName: String) {
        if(teamA) {
            this.teamAName = newTeamName
        }
        else {
            this.teamBName = newTeamName
        }
    }

    val winningTeam: Char
        get() = when {
            teamAScore > teamBScore -> TEAM_A_WINNING
            teamAScore < teamBScore -> TEAM_B_WINNING
            else -> TIE
        }

    val getId: UUID
        get() = id

    val getDate: Date
        get() = date

    val getTeamAName: String
        get() = teamAName

    val getTeamBName: String
        get() = teamBName

    val getTeamAScore: Int
        get() = teamAScore

    val getTeamBScore: Int
        get() = teamBScore
}

