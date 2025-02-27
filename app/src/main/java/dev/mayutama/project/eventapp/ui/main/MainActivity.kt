package dev.mayutama.project.eventapp.ui.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dev.mayutama.project.eventapp.R
import dev.mayutama.project.eventapp.base.BaseActivity
import dev.mayutama.project.eventapp.databinding.ActivityMainBinding

class MainActivity :
    BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate)
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init(){
        setupBottomNav()
    }

    private fun setupBottomNav(){
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }
}