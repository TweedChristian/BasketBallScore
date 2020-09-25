package com.tweedchristian.android.basketballscore

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val TEAM_ONE_INDEX = "teamOne"
private const val TEAM_TWO_INDEX = "teamTwo"

//TODO: Recycler View Stuff
class MainActivity : AppCompatActivity() {

    private lateinit var teamOne3ShotButton: Button
    private lateinit var teamTwo3ShotButton: Button
    private lateinit var teamOne2ShotButton: Button
    private lateinit var teamTwo2ShotButton: Button
    private lateinit var teamOneFreeThrowButton: Button
    private lateinit var teamTwoFreeThrowButton: Button
    private lateinit var resetButton: Button
    private lateinit var saveButton: Button
    private lateinit var displayButton: Button

    private lateinit var teamOnePointsTextView: TextView
    private lateinit var teamTwoPointsTextView: TextView
    private lateinit var teamOneTitle: EditText
    private lateinit var teamTwoTitle: EditText

    private lateinit var linearLayout: LinearLayout

    private val basketballViewModel: BasketballViewModel by lazy {
        ViewModelProvider(this).get(BasketballViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        teamButtonInit()
        val teamOneInitialPoints = savedInstanceState?.getInt(TEAM_ONE_INDEX, 0) ?: 0
        val teamTwoInitialPoints = savedInstanceState?.getInt(TEAM_TWO_INDEX, 0) ?: 0
        basketballViewModel.setPointsFromSavedState(teamOneInitialPoints, teamTwoInitialPoints)
        updatePoints()
        val currentFragment = supportFragmentManager.findFragmentById(R.id.titleFragmentContainer)
        if(currentFragment == null) {
//            val fragment = TitleFragment()
            val fragment = GameListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.titleFragmentContainer, fragment)
                .commit()
        }
    }



    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)

        savedInstanceState.putInt(TEAM_ONE_INDEX, basketballViewModel.teamOneCurrentPoints)
        savedInstanceState.putInt(TEAM_TWO_INDEX, basketballViewModel.teamTwoCurrentPoints)
    }

    /** For Activity to Activity*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, resultCode.toString())
        Log.d(TAG, Activity.RESULT_OK.toString())
        if(resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, R.string.gameResultsString, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        val teamOneTitleWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                basketballViewModel.basketBallGame.updateTeamName(true, s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }
        val teamTwoTitleWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               basketballViewModel.basketBallGame.updateTeamName(false, s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }
        teamOneTitle.addTextChangedListener(teamOneTitleWatcher)
        teamTwoTitle.addTextChangedListener(teamTwoTitleWatcher)
    }

    /**
     * A function to update the displayed text to what is stored in the view model
     */
    private fun updatePoints() {
        teamOnePointsTextView.text = basketballViewModel.teamOneCurrentPoints.toString()
        teamTwoPointsTextView.text = basketballViewModel.teamTwoCurrentPoints.toString()
    }

    /**
     * A function that finds all the buttons and text views and set up callbacks
     */
    private fun teamButtonInit() {
        //Team One Init
        teamOne3ShotButton = findViewById(R.id.teamOne3Points)
        teamOne2ShotButton = findViewById(R.id.teamOne2Points)
        teamOneFreeThrowButton = findViewById(R.id.teamOneFreeThrow)
        teamOnePointsTextView = findViewById(R.id.teamOnePoints)
        teamOneTitle = findViewById(R.id.teamOneName)
        teamOneTitle.clearFocus()

        //Team Two Init
        teamTwo3ShotButton = findViewById(R.id.teamTwo3Points)
        teamTwo2ShotButton = findViewById(R.id.teamTwo2Points)
        teamTwoFreeThrowButton = findViewById(R.id.teamTwoFreeThrow)
        teamTwoPointsTextView = findViewById(R.id.teamTwoPoints)
        teamTwoTitle = findViewById(R.id.teamTwoName)
        teamTwoTitle.clearFocus()

        //Utility Buttons
        resetButton = findViewById(R.id.resetButton)
        saveButton = findViewById(R.id.saveButton)
        displayButton = findViewById(R.id.displayButton)

        //Root Element
        linearLayout = findViewById(R.id.root)

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
            basketballViewModel.updatePoints(false, 2)
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
        /** For Activity to Activity*/
        saveButton.setOnClickListener{
            val intent = GameResults.newIntent(this@MainActivity, basketballViewModel.basketBallGame)
            startActivityForResult(intent, 0)
        }

        //Hide Keyboard and Clear Focus on Click-away
        linearLayout.setOnClickListener {
            val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

            if(teamOneTitle.hasFocus()) {
                imm.hideSoftInputFromWindow(teamOneTitle.windowToken, 0)
            }

            if(teamTwoTitle.hasFocus()) {
                imm.hideSoftInputFromWindow(teamTwoTitle.windowToken, 0)
            }

            teamOneTitle.clearFocus()
            teamTwoTitle.clearFocus()
        }
    }
}

