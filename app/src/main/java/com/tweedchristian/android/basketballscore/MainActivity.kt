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
            val game = BasketBallGame()
            val fragment = GameDetailFragment.newInstance(game.getTeamOne.name, game.getTeamTwo.name, game.getTeamOne.points, game.getTeamTwo.points)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.mainFragmentContainer, fragment)
                .commit()
        }
    }

    override fun loadWinningList(winningTeam: Char) {
        Log.i(TAG, "Received a call to load the winning list of games")
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

    override fun loadGame(game: BasketBallGame) {
        Log.i(TAG, "Loading a basketball game with id: ${game.id}")
        val fragment = GameDetailFragment.newInstance(game.getTeamOne.name, game.getTeamTwo.name, game.getTeamOne.points, game.getTeamTwo.points)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }



    /** For Activity to Activity*/
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        Log.d(TAG, resultCode.toString())
//        Log.d(TAG, Activity.RESULT_OK.toString())
//        if(resultCode == Activity.RESULT_OK) {
//            Toast.makeText(this, R.string.gameResultsString, Toast.LENGTH_SHORT).show()
//        }
//    }
//        /** For Activity to Activity*/
//        saveButton.setOnClickListener{
//            val intent = GameResults.newIntent(this@MainActivity, basketballViewModel.basketBallGame)
//            startActivityForResult(intent, 0)
//        }
//
//
}

