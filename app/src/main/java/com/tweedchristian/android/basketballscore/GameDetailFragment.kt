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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.util.*

private const val TAG = "GameDetailFragment"

private const val ARGS_UUID = "uuid"
private const val ARGS_DATE = "date"

class GameDetailFragment : Fragment() {
    interface Callbacks {
        fun loadWinningList(winningTeam: Char)
    }

    private var callbacks: Callbacks? = null

    private lateinit var game: Game

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
        val id = arguments?.getSerializable(ARGS_UUID) as UUID
        basketballViewModel.loadGameById(id)
        //If we want to load a bunch of fresh games
        basketballViewModel.setupRepo(150)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_game_detail, container, false)
        teamButtonInit(view)
        return view
    }

    /**Just in case*/
//    val teamOneInitialPoints = savedInstanceState?.getInt(TEAM_ONE_INDEX, 0) ?: 0
//    val teamTwoInitialPoints = savedInstanceState?.getInt(TEAM_TWO_INDEX, 0) ?: 0
//    val teamANameInitial = savedInstanceState?.getString(TEAM_A_NAME, "Team A") ?: "Team A"
//    val teamBNameInitial = savedInstanceState?.getString(TEAM_B_NAME, "Team B") ?: "Team B"
//    basketballViewModel.loadDataFromNewInstance(teamOneInitialPoints, teamTwoInitialPoints, teamANameInitial, teamBNameInitial)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        basketballViewModel.gameLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { game ->
                game?.let{
                    this.game = game

                    updateTeams()
                }
            }
        )
    }

    override fun onStop() {
        super.onStop()
        basketballViewModel.saveGame(game)
    }

    companion object {
        fun newInstance(id: UUID): GameDetailFragment {
            var args = Bundle()
                .apply {
                    putSerializable(ARGS_UUID, id)
                }
//                .apply {
//                    putString(ARGS_TEAM_ONE_NAME, teamOneName)
//                }
//                .apply {
//                  putString(ARGS_TEAM_TWO_NAME, teamTwoName)
//                }
////                .apply {
////                    putString(ARGS_DATE, date.toString())
////                }
//                .apply {
//                    putInt(ARGS_TEAM_ONE_POINTS, teamOnePoints)
//                }
//                .apply {
//                    putInt(ARGS_TEAM_TWO_POINTS, teamTwoPoints)
//                }
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
                game.teamAName = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }
        val teamTwoTitleWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                game.teamBName = s.toString()
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
        teamOnePointsTextView.text = game.teamAScore.toString()
        teamTwoPointsTextView.text = game.teamBScore.toString()
        teamOneTitle.setText(game.teamAName)
        teamTwoTitle.setText(game.teamBName)
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
            game.teamAScore += 3
            updateTeams()
        }

        teamOne2ShotButton.setOnClickListener {
            game.teamAScore += 2
            updateTeams()
        }

        teamOneFreeThrowButton.setOnClickListener {
            game.teamAScore += 1
            updateTeams()
        }

        teamTwo3ShotButton.setOnClickListener {
            game.teamBScore += 3
            updateTeams()
        }

        teamTwo2ShotButton.setOnClickListener {
            game.teamBScore += 2
            updateTeams()
        }

        teamTwoFreeThrowButton.setOnClickListener {
            game.teamBScore += 1
            updateTeams()
        }

        saveButton.setOnClickListener {
            basketballViewModel.saveGame(game)
//            Toast.makeText(
//                this,
//                R.string.saved,
//                Toast.LENGTH_SHORT
//            ).show()
        }

        resetButton.setOnClickListener {
            game.teamAScore = 0
            game.teamBScore = 0
            updateTeams()
        }

        displayButton.setOnClickListener {
            callbacks?.loadWinningList(game.winningTeam)
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