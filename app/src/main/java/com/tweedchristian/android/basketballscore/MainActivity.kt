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
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(),
    GameDetailFragment.Callbacks,
    GameListFragment.Callbacks
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.mainFragmentContainer)
        if(currentFragment == null) {
            val game = Game()
            val fragment = GameDetailFragment.newInstance(game.teamAName, game.teamBName, game.teamAScore, game.teamBScore)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.mainFragmentContainer, fragment)
                .commit()
        }
    }

    override fun loadWinningList(winningTeam: Char) {
        val fragment = GameListFragment.newInstance(winningTeam)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun loadGameById(id: UUID) {
        TODO("Load From DB")
    }

    override fun loadGame(game: Game) {
        val fragment = GameDetailFragment.newInstance(game.teamAName, game.teamBName, game.teamAScore, game.teamBScore)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}

