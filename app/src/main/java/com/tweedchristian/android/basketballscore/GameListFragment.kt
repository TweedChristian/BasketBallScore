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
    }
    private var callbacks: Callbacks? = null

    private var adapter: GameAdapter? = GameAdapter(emptyList())
    private var winningTeam: Char = TIE
    private lateinit var gameRecyclerView: RecyclerView

    private val gameListViewModel: BasketBallGameListViewModel by lazy {
        ViewModelProvider(this).get(BasketBallGameListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        winningTeam = arguments?.getChar(ARG_WINNING_TEAM) ?: TIE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_list, container, false)
        gameRecyclerView = view.findViewById(R.id.gameRecyclerView)
        gameRecyclerView.layoutManager = LinearLayoutManager(context)
        gameRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameListViewModel.publicGames.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { games ->
                games?.let {
                    updateUI(games)
                }
            }
        )
    }

    private fun updateUI(games: List<Game>) {

        val filteredGames = when (winningTeam) {
            TEAM_A_WINNING -> games.filter{ it.winningTeam == TEAM_A_WINNING }
            TEAM_B_WINNING -> games.filter{ it.winningTeam == TEAM_B_WINNING }
            else -> games
        }
        adapter = GameAdapter(filteredGames)
        gameRecyclerView.adapter = adapter
        }

    private inner class GameHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var game: Game
        val dateTextView: TextView = itemView.findViewById(R.id.gameDate)
        val teamOneNameTextView: TextView = itemView.findViewById(R.id.listTeamOneName)
        val teamTwoNameTextView: TextView = itemView.findViewById(R.id.listTeamTwoName)
        val teamScoreTextView: TextView = itemView.findViewById(R.id.teamScore)
        val winnerImageView: ImageView = itemView.findViewById(R.id.imageView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            callbacks?.loadGameById(game.id)
        }

        fun bind(game: Game) {
            this.game = game
            dateTextView.text = game.date.toString()
            teamOneNameTextView.text = game.teamAName
            teamTwoNameTextView.text = game.teamBName
            teamScoreTextView.text = loadGameScore(game.teamAScore, game.teamBScore)
            when {
                game.teamAScore > game.teamBScore -> winnerImageView.setImageResource(R.drawable.keet)
                game.teamAScore < game.teamBScore -> winnerImageView.setImageResource(R.drawable.kobaka)
                else -> winnerImageView.setImageResource(R.drawable.minion)
            }
        }

        private fun loadGameScore(teamOneScore: Int, teamTwoScore: Int): String {
            return "$teamOneScore : $teamTwoScore"
        }
    }

    private inner class GameAdapter(var games: List<Game>): RecyclerView.Adapter<GameHolder>() {
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