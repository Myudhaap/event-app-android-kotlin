package dev.mayutama.project.eventapp.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dev.mayutama.project.eventapp.R
import dev.mayutama.project.eventapp.base.BaseActivity
import dev.mayutama.project.eventapp.databinding.ActivityMainBinding

class MainActivity :
    BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate)
{
    private val mainViewModel: MainViewModel by viewModels(){ MainViewModelFactory.getInstance(this.application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        checkTheme()
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init(){
        setupBottomNav()
    }

    private fun setupBottomNav(){
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id) {
                R.id.navigation_home -> setTitleAppBar(getString(R.string.title_home))
                R.id.navigation_upcoming -> setTitleAppBar(getString(R.string.upcoming))
                R.id.navigation_finished -> setTitleAppBar(getString(R.string.finished))
                R.id.navigation_favorite -> setTitleAppBar(getString(R.string.favorite))
                R.id.navigation_setting -> setTitleAppBar(getString(R.string.setting))
            }
        }
        navView.setupWithNavController(navController)
    }

    private fun setTitleAppBar(title: String) {
        binding.tvTitleApp.text = title
    }

    private fun checkTheme() {
        mainViewModel.getThemeSetting().observe(this){
            if(it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}