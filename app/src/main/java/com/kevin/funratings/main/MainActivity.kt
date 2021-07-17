package com.kevin.funratings.main

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kevin.funratings.R
import com.kevin.funratings.RatingsApplication
import com.kevin.funratings.databinding.ActivityMainBinding
import com.kevin.funratings.deckimport.DeckImportFragment
import com.kevin.funratings.detail.CardDetailFragment
import com.kevin.funratings.home.HomeFragment
import com.kevin.funratings.stats.StatsFragment

class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModels { MainViewModelFactory((application as RatingsApplication).repository) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //loadHome()
        setUpObservers()
        if (viewModel.currentFragmentTag != "") return
        replaceFragment("home")
    }

    private fun setUpObservers() {
        viewModel.locale.observe(this) {
            Log.d("hello", "nice $it")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_clear -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle("are you sure you want to clear")
                    .setPositiveButton("yes") { _: DialogInterface, _: Int ->
                        viewModel.deleteAllEntries()
                    }
                    .setNegativeButton("no") { _: DialogInterface, _: Int ->
                    }
                    .create()
                    .show()
                true
            }
            R.id.all_ratings -> {
                replaceFragment("ratings", viewModel.locale.value)
                true
            }
            R.id.temp -> {
                replaceFragment("deckimport", viewModel.locale.value)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun replaceFragment(type: String, extra: String? = null, clan: String? = null) {
        Log.d("im in here", "$type nice")
        val wasNull = supportFragmentManager.findFragmentByTag(type) == null
        val fragment = supportFragmentManager.findFragmentByTag(type) ?:
        when(type) {
                "home" -> HomeFragment.newInstance()
                "ratings" -> StatsFragment.newInstance(extra?: "ja")
                "deckimport" -> DeckImportFragment.newInstance(extra ?: "ja")
                else -> CardDetailFragment.newInstance(viewModel.locale.value ?: "ja", type, clan ?: "0")
            }


        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fcvMainContainer, fragment, type)

        if (type != "home" && wasNull) transaction.addToBackStack(type)
        transaction.commit()
        viewModel.currentFragmentTag = type
    }

    fun updateLocale(locale:String) {
        viewModel.locale.postValue(locale)
    }

    fun onItemClick(type: String, extra: String? = null, clan: String? = null) {
        replaceFragment(type, extra, clan)
    }
}