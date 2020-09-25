package com.tweedchristian.android.basketballscore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

private const val TAG = "GameListFragment"

class GameListFragment : Fragment() {
    private var adapter: GameAdapter? = null
    private lateinit var gameRecyclerView: RecyclerView

    private val gameListViewModel: BasketBallGameListViewModel by lazy {
        ViewModelProvider(this).get(BasketBallGameListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val games = gameListViewModel.games
        adapter = GameAdapter(games)
        gameRecyclerView.adapter = adapter
    }

    private inner class GameHolder(view: View): RecyclerView.ViewHolder(view) {
        private lateinit var game: BasketBallGame
        val dateTextView: TextView = itemView.findViewById(R.id.gameDate)
        val teamOneNameTextView: TextView = itemView.findViewById(R.id.listTeamOneName)
        val teamTwoNameTextView: TextView = itemView.findViewById(R.id.listTeamTwoName)
        val teamScoreTextView: TextView = itemView.findViewById(R.id.teamScore)

        fun bind(game: BasketBallGame) {
            this.game = game
            dateTextView.text = game.date.toString()
            teamOneNameTextView.text = game.getTeamOne.name
            teamTwoNameTextView.text = game.getTeamTwo.name
            teamScoreTextView.text = loadGameScore(game.getTeamOne.points, game.getTeamTwo.points)
        }

        private fun loadGameScore(teamOneScore: Int, teamTwoScore: Int): String {
            return "$teamOneScore:$teamTwoScore"
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
        fun newInstance(): GameListFragment {
            return GameListFragment()
        }
    }

}