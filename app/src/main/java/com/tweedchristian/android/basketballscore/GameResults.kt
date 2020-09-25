package com.tweedchristian.android.basketballscore

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

private const val TEAM_A_NAME = "TEAM_A_NAME"
private const val TEAM_B_NAME = "TEAM_B_NAME"
private const val TEAM_A_POINTS = "TEAM_A_POINTS"
private const val TEAM_B_POINTS = "TEAM_B_POINTS"

class GameResults : AppCompatActivity() {

    private lateinit var teamAName: TextView
    private lateinit var teamBName: TextView
    private lateinit var teamAPoints: TextView
    private lateinit var teamBPoints: TextView
    private lateinit var backButton: Button

    private val basketballViewModel: BasketballViewModel by lazy {
        ViewModelProvider(this).get(BasketballViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_results)
        val teamOneInitialPoints = savedInstanceState?.getInt(TEAM_A_POINTS, 0) ?: 0
        val teamTwoInitialPoints = savedInstanceState?.getInt(TEAM_B_POINTS, 0) ?: 0
        loadGame(teamOneInitialPoints, teamTwoInitialPoints)
        initViews()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(TEAM_A_POINTS, basketballViewModel.teamOneCurrentPoints)
        savedInstanceState.putInt(TEAM_B_POINTS, basketballViewModel.teamTwoCurrentPoints)
    }

    companion object {
        fun newIntent(packageContext: Context, basketBallGame: BasketBallGame): Intent {
            return Intent(packageContext, GameResults::class.java)
                .apply {
                    putExtra(TEAM_A_NAME, basketBallGame.getTeamOne.name)
                }
                .apply {
                    putExtra(TEAM_B_NAME, basketBallGame.getTeamTwo.name)
                }
                .apply {
                    putExtra(TEAM_A_POINTS, basketBallGame.getTeamOne.points)
                }
                .apply {
                    putExtra(TEAM_B_POINTS, basketBallGame.getTeamTwo.points)
                }
        }
    }

    private fun loadGame(bundlePointsA: Int, bundlePointsB: Int) {
        val intentPointsA = intent.getIntExtra(TEAM_A_POINTS, 0)
        val intentPointsB = intent.getIntExtra(TEAM_B_POINTS, 0)
        if(intentPointsA == 0 && intentPointsB == 0){
            basketballViewModel.setPointsFromSavedState(bundlePointsA, bundlePointsB)
        }
        else {
            basketballViewModel.setPointsFromSavedState(intentPointsA, intentPointsB)
        }

        basketballViewModel.basketBallGame.updateTeamName(true,  intent.getStringExtra(TEAM_A_NAME) ?: "Team A")
        basketballViewModel.basketBallGame.updateTeamName(false,  intent.getStringExtra(TEAM_B_NAME) ?: "Team B")
    }

    private fun initViews(){
        backButton = findViewById(R.id.backButton)
        teamAName = findViewById(R.id.gameResultsTeamA)
        teamBName = findViewById(R.id.gameResultsTeamB)
        teamAPoints = findViewById(R.id.gameResultsTeamAPoints)
        teamBPoints = findViewById(R.id.gameResultsTeamBPoints)

        teamAName.text = basketballViewModel.basketBallGame.getTeamOne.name
        teamBName.text = basketballViewModel.basketBallGame.getTeamTwo.name
        teamAPoints.text = basketballViewModel.basketBallGame.getTeamOne.points.toString()
        teamBPoints.text = basketballViewModel.basketBallGame.getTeamTwo.points.toString()

        backButton.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }
}