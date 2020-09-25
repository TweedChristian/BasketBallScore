package com.tweedchristian.android.basketballscore

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.util.*

private const val TAG = "GameListFragment"
private const val ARG_WINNING_TEAM = "winningTeam"

class GameListFragment : Fragment() {
    interface Callbacks {
        fun loadGameById(id: UUID)
        fun loadGame(game: BasketBallGame)
    }
    private var callbacks: Callbacks? = null

    private var adapter: GameAdapter? = null
    private var winningTeam: Char = TIE
    private lateinit var gameRecyclerView: RecyclerView

    private val gameListViewModel: BasketBallGameListViewModel by lazy {
        ViewModelProvider(this).get(BasketBallGameListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        Log.i(TAG, "Game List Fragment context loaded")
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        Log.i(TAG, "Game List Fragment unattatched")
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        winningTeam = arguments?.getChar(ARG_WINNING_TEAM) ?: TIE
        Log.i(TAG, "Argument loaded: $winningTeam")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_list, container, false)
        gameRecyclerView = view.findViewById(R.id.gameRecyclerView)
        gameRecyclerView.layoutManager = LinearLayoutManager(context)
        updateUI()
        return view
    }

    private fun updateUI() {
        val games = when (winningTeam) {
            TEAM_A_WINNING -> gameListViewModel.games.filter{ it.winningTeam == TEAM_A_WINNING }
            TEAM_B_WINNING -> gameListViewModel.games.filter{ it.winningTeam == TEAM_B_WINNING }
            else -> gameListViewModel.games
        }
        Log.i(TAG, "Length of filtered games: ${games.size}")
        adapter = GameAdapter(games)
        gameRecyclerView.adapter = adapter
        }

    private inner class GameHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var game: BasketBallGame
        val dateTextView: TextView = itemView.findViewById(R.id.gameDate)
        val teamOneNameTextView: TextView = itemView.findViewById(R.id.listTeamOneName)
        val teamTwoNameTextView: TextView = itemView.findViewById(R.id.listTeamTwoName)
        val teamScoreTextView: TextView = itemView.findViewById(R.id.teamScore)
        val winnerImageView: ImageView = itemView.findViewById(R.id.imageView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.i(TAG, "Game list item clicked")
            callbacks?.loadGame(game)
        }

        fun bind(game: BasketBallGame) {
            this.game = game
            dateTextView.text = game.date.toString()
            teamOneNameTextView.text = game.getTeamOne.name
            teamTwoNameTextView.text = game.getTeamTwo.name
            teamScoreTextView.text = loadGameScore(game.getTeamOne.points, game.getTeamTwo.points)
            when {
                game.getTeamOne.points > game.getTeamTwo.points -> winnerImageView.setImageResource(R.drawable.keet)
                game.getTeamOne.points < game.getTeamTwo.points -> winnerImageView.setImageResource(R.drawable.kobaka)
                else -> winnerImageView.setImageResource(R.drawable.minion)
            }
        }

        private fun loadGameScore(teamOneScore: Int, teamTwoScore: Int): String {
            return "$teamOneScore : $teamTwoScore"
        }
    }

    private inner class GameAdapter(var games: List<BasketBallGame>): RecyclerView.Adapter<GameHolder>() {
        override fun getItemCount() = games.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
            val view = layoutInflater.inflate(R.layout.list_item_game, parent, false)
            return GameHolder(view)
        }

        override fun onBindViewHolder(holder: GameHolder, position: Int) {
            val game = games[position]
            holder.bind(game)
        }
    }

    companion object {
        fun newInstance(winningTeam: Char): GameListFragment {
            val fragArgs = Bundle().apply {
                putChar(ARG_WINNING_TEAM, winningTeam)
            }
            return GameListFragment().apply {
                arguments = fragArgs
            }
        }
    }

}