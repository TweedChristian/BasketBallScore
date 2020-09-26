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
            //Dummy Id to load a game first
            val fragment = GameDetailFragment.newInstance(UUID.fromString("3d50a644-0b29-4510-8528-b1950f2e39e1"))
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
        val fragment = GameDetailFragment.newInstance(id)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}

