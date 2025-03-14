package dev.mayutama.project.eventapp.ui.main

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dev.mayutama.project.eventapp.R
import dev.mayutama.project.eventapp.base.BaseActivity
import dev.mayutama.project.eventapp.databinding.ActivityMainBinding
import dev.mayutama.project.eventapp.util.Util
import dev.mayutama.project.eventapp.util.hide
import dev.mayutama.project.eventapp.util.show

class MainActivity :
    BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    OnClickListener
{
    private val mainViewModel: MainViewModel by viewModels{ MainViewModelFactory.getInstance(this.application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        checkTheme()
        super.onCreate(savedInstanceState)

        init()
        initListener()
    }

    override fun onBackPressed() {
        if(binding.searchView.isShowing){
            binding.searchView.hide()
        }else{
            super.onBackPressed()
        }
    }

    private fun init(){
        setSupportActionBar(binding.appBar)
        setupBottomNav()

        binding.searchView
            .editText
            .setOnEditorActionListener { textView, i, keyEvent ->
                binding.searchView.hide()

                false
            }
    }

    private fun initListener(){
        binding.imgSearch.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.img_search -> onClickSearch()
        }
    }

    fun onClickSearch(){
        binding.searchView.show()
    }

    private fun setupBottomNav(){
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            binding.imgSearch.show()
            binding.imgNotification.show()
            binding.tvCountNotif.show()
            when(destination.id) {
                R.id.navigation_home -> {
                    binding.imgSearch.hide()
                    setTitleAppBar(getString(R.string.title_home))
                }
                R.id.navigation_upcoming -> setTitleAppBar(getString(R.string.upcoming))
                R.id.navigation_finished -> setTitleAppBar(getString(R.string.finished))
                R.id.navigation_favorite -> setTitleAppBar(getString(R.string.favorite))
                R.id.navigation_setting -> {
                    binding.imgSearch.hide()
                    binding.imgNotification.hide()
                    binding.tvCountNotif.hide()
                    setTitleAppBar(getString(R.string.setting))
                }
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

    fun showLoading(){
        binding.loadingLayout.root.visibility = View.VISIBLE
        Util.disableScreenAction(window)
    }

    fun hideLoading(){
        binding.loadingLayout.root.visibility = View.GONE
        Util.enableScreenAction(window)
    }
}