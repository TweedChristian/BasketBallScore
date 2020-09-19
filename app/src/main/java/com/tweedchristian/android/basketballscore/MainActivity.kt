package com.tweedchristian.android.basketballscore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val TEAM_ONE_INDEX = "teamOne"
private const val TEAM_TWO_INDEX = "teamTwo"

//TODO: 2nd Activity (Previous Scores)
//TODO: 2nd Activity Layout
//TODO: 2nd Activity Activity Manager

//TODO: Fragment

//TODO: Recycler View Stuff
class MainActivity : AppCompatActivity() {

    private lateinit var teamOne3ShotButton: Button
    private lateinit var teamTwo3ShotButton: Button
    private lateinit var teamOne2ShotButton: Button
    private lateinit var teamTwo2ShotButton: Button
    private lateinit var teamOneFreeThrowButton: Button
    private lateinit var teamTwoFreeThrowButton: Button
    private lateinit var resetButton: Button
    private lateinit var teamOnePointsTextView: TextView
    private lateinit var teamTwoPointsTextView: TextView

    private val basketballViewModel: BasketballViewModel by lazy {
        ViewModelProvider(this).get(BasketballViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Log.i(TAG, "onCreate Called")
        teamButtonInit()
        val teamOneInitialPoints = savedInstanceState?.getInt(TEAM_ONE_INDEX, 0) ?: 0
        val teamTwoInitialPoints = savedInstanceState?.getInt(TEAM_TWO_INDEX, 0) ?: 0
//        Log.i(TAG, "Team One:$teamOneInitialPoints")
//        Log.i(TAG, "Team Two: $teamTwoInitialPoints")
        basketballViewModel.setPointsFromSavedState(teamOneInitialPoints, teamTwoInitialPoints)
        updatePoints()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
//        Log.i(TAG, "onSaveInstanceState")
//        Log.i(TAG, "Storing point amounts: " +
//                "${basketballViewModel.teamOneCurrentPoints}, " +
//                "${basketballViewModel.teamTwoCurrentPoints}")
        savedInstanceState.putInt(TEAM_ONE_INDEX, basketballViewModel.teamOneCurrentPoints)
        savedInstanceState.putInt(TEAM_TWO_INDEX, basketballViewModel.teamTwoCurrentPoints)
    }

    /**
     * A function to update the displayed text to what is stored in the view model
     */
    private fun updatePoints() {
//        Log.i(TAG, "Updating the text view")
        teamOnePointsTextView.text = basketballViewModel.teamOneCurrentPoints.toString()
        teamTwoPointsTextView.text = basketballViewModel.teamTwoCurrentPoints.toString()
    }

    /**
     * A function that finds all the buttons and text views and set up callbacks
     */
    private fun teamButtonInit() {
    //    Log.i(TAG, "Initializing and loading buttons")
        //Team One Init
        teamOne3ShotButton = findViewById(R.id.teamOne3Points)
        teamOne2ShotButton = findViewById(R.id.teamOne2Points)
        teamOneFreeThrowButton = findViewById(R.id.teamOneFreeThrow)
        teamOnePointsTextView = findViewById(R.id.teamOnePoints)

        //Team Two Init
        teamTwo3ShotButton = findViewById(R.id.teamTwo3Points)
        teamTwo2ShotButton = findViewById(R.id.teamTwo2Points)
        teamTwoFreeThrowButton = findViewById(R.id.teamTwoFreeThrow)
        teamTwoPointsTextView = findViewById(R.id.teamTwoPoints)

        //Reset Button
        resetButton = findViewById(R.id.resetButton)

        //Setting Callbacks

        teamOne3ShotButton.setOnClickListener {
            basketballViewModel.updatePoints(true, 3)
            updatePoints()
        }

        teamOne2ShotButton.setOnClickListener {
            basketballViewModel.updatePoints(true, 2)
            updatePoints()
        }

        teamOneFreeThrowButton.setOnClickListener {
            basketballViewModel.updatePoints(true, 1)
            updatePoints()
        }

        teamTwo3ShotButton.setOnClickListener {
            basketballViewModel.updatePoints(false, 3)
            updatePoints()
        }

        teamTwo2ShotButton.setOnClickListener {
            basketballViewModel.updatePoints(false,2)
            updatePoints()
        }

        teamTwoFreeThrowButton.setOnClickListener {
            basketballViewModel.updatePoints(false, 1)
            updatePoints()
        }

        resetButton.setOnClickListener {
            basketballViewModel.resetPoints()
            updatePoints()
        }
    }
}