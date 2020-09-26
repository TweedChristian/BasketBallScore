package com.tweedchristian.android.basketballscore

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

private const val TAG = "GameDetailFragment"
private const val TEAM_ONE_INDEX = "teamOne"
private const val TEAM_TWO_INDEX = "teamTwo"

private const val ARGS_UUID = "uuid"
private const val ARGS_DATE = "date"
private const val ARGS_TEAM_ONE_NAME = "teamOneName"
private const val ARGS_TEAM_TWO_NAME = "teamTwoName"
private const val ARGS_TEAM_ONE_POINTS = "teamOnePoints"
private const val ARGS_TEAM_TWO_POINTS = "teamTwoPoints"

class GameDetailFragment : Fragment() {
    interface Callbacks {
        fun loadWinningList(winningTeam: Char)
    }

    private var callbacks: Callbacks? = null

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

    private val basketballViewModel: BasketballViewModel by lazy {
        ViewModelProvider(this).get(BasketballViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val teamOneName = arguments?.getString(ARGS_TEAM_ONE_NAME, "Team A") ?: "Team A"
        val teamTwoName = arguments?.getString(ARGS_TEAM_TWO_NAME, "Team B") ?: "Team B"
        val teamOnePoints = arguments?.getInt(ARGS_TEAM_ONE_POINTS, 0) ?: 0
        val teamTwoPoints = arguments?.getInt(ARGS_TEAM_TWO_POINTS, 0) ?: 0
        basketballViewModel.loadDataFromNewInstance(teamOnePoints, teamTwoPoints, teamOneName, teamTwoName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_detail, container, false)
        teamButtonInit(view)
        updateTeams()
        return view

        /**Just in case*/
        //       val teamOneInitialPoints = savedInstanceState?.getInt(TEAM_ONE_INDEX, 0) ?: 0
//        val teamTwoInitialPoints = savedInstanceState?.getInt(TEAM_TWO_INDEX, 0) ?: 0
//        basketballViewModel.setPointsFromSavedState(teamOneInitialPoints, teamTwoInitialPoints)
    }

//    override fun onSaveInstanceState(savedInstanceState: Bundle) {
//        super.onSaveInstanceState(savedInstanceState)
//
//        savedInstanceState.putInt(TEAM_ONE_INDEX, basketballViewModel.teamOneCurrentPoints)
//        savedInstanceState.putInt(TEAM_TWO_INDEX, basketballViewModel.teamTwoCurrentPoints)
//    }

    companion object {
        fun newInstance(teamOneName: String, teamTwoName: String, teamOnePoints: Int, teamTwoPoints: Int): GameDetailFragment {
            var args = Bundle()
//                .apply {
//                    putSerializable(ARGS_UUID, gameId)
//                }
                .apply {
                    putString(ARGS_TEAM_ONE_NAME, teamOneName)
                }
                .apply {
                  putString(ARGS_TEAM_TWO_NAME, teamTwoName)
                }
//                .apply {
//                    putString(ARGS_DATE, date.toString())
//                }
                .apply {
                    putInt(ARGS_TEAM_ONE_POINTS, teamOnePoints)
                }
                .apply {
                    putInt(ARGS_TEAM_TWO_POINTS, teamTwoPoints)
                }
            return GameDetailFragment().apply {
                arguments = args
            }
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    /**
     * A function to update the displayed text to what is stored in the view model
     */
    private fun updateTeams() {
        teamOnePointsTextView.text = basketballViewModel.teamOneCurrentPoints.toString()
        teamTwoPointsTextView.text = basketballViewModel.teamTwoCurrentPoints.toString()
        teamOneTitle.setText(basketballViewModel.basketBallGame.teamAName)
        teamTwoTitle.setText(basketballViewModel.basketBallGame.teamBName)
    }

    /**
     * A function that finds all the buttons and text views and set up callbacks
     */
    private fun teamButtonInit(view: View) {
        //Team One Init
        teamOne3ShotButton = view.findViewById(R.id.teamOne3Points)
        teamOne2ShotButton = view.findViewById(R.id.teamOne2Points)
        teamOneFreeThrowButton = view.findViewById(R.id.teamOneFreeThrow)
        teamOnePointsTextView = view.findViewById(R.id.teamOnePoints)
        teamOneTitle = view.findViewById(R.id.teamOneName)
        teamOneTitle.clearFocus()

        //Team Two Init
        teamTwo3ShotButton = view.findViewById(R.id.teamTwo3Points)
        teamTwo2ShotButton = view.findViewById(R.id.teamTwo2Points)
        teamTwoFreeThrowButton = view.findViewById(R.id.teamTwoFreeThrow)
        teamTwoPointsTextView = view.findViewById(R.id.teamTwoPoints)
        teamTwoTitle = view.findViewById(R.id.teamTwoName)
        teamTwoTitle.clearFocus()

//        Utility Buttons
        resetButton = view.findViewById(R.id.resetButton)
        saveButton = view.findViewById(R.id.saveButton)
        displayButton = view.findViewById(R.id.displayButton)

//        Setting Callbacks

        teamOne3ShotButton.setOnClickListener {
            basketballViewModel.updatePoints(true, 3)
            updateTeams()
        }

        teamOne2ShotButton.setOnClickListener {
            basketballViewModel.updatePoints(true, 2)
            updateTeams()
        }

        teamOneFreeThrowButton.setOnClickListener {
            basketballViewModel.updatePoints(true, 1)
            updateTeams()
        }

        teamTwo3ShotButton.setOnClickListener {
            basketballViewModel.updatePoints(false, 3)
            updateTeams()
        }

        teamTwo2ShotButton.setOnClickListener {
            basketballViewModel.updatePoints(false, 2)
            updateTeams()
        }

        teamTwoFreeThrowButton.setOnClickListener {
            basketballViewModel.updatePoints(false, 1)
            updateTeams()
        }

        resetButton.setOnClickListener {
            basketballViewModel.resetPoints()
            updateTeams()
        }

        displayButton.setOnClickListener {
            callbacks?.loadWinningList(basketballViewModel.winningTeam)
        }

//        Hide Keyboard and Clear Focus on Click-away
        view.setOnClickListener {
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

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