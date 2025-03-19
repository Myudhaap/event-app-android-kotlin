package dev.mayutama.project.eventapp.ui.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.mayutama.project.eventapp.R
import dev.mayutama.project.eventapp.base.BaseActivity
import dev.mayutama.project.eventapp.databinding.ActivityMainBinding
import dev.mayutama.project.eventapp.util.Result
import dev.mayutama.project.eventapp.util.Util
import dev.mayutama.project.eventapp.util.hide
import dev.mayutama.project.eventapp.util.show

class MainActivity :
    BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    OnClickListener
{
    private val mainViewModel: MainViewModel by viewModels{ MainViewModelFactory.getInstance(this.application) }
    private lateinit var adapter: SearchEventAdapter

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        checkTheme()
        super.onCreate(savedInstanceState)

        init()
        initListener()
    }

    private fun init(){
        setSupportActionBar(binding.appBar)
        setupBottomNav()

        if(Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        binding.searchView
            .editText
            .setOnEditorActionListener { textView, _, _ ->
                if(textView.text.toString().isEmpty()){
                    Util.showToast(this@MainActivity, "Input event name...")
                }else{
                    mainViewModel.getEventSearch(textView.text.toString())
                }
                false
            }

        observeEventSearch()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.searchView.isShowing){
                    binding.searchView.let {
                        it.editText.setText("")
                        adapter.submitList(null)
                        binding.tvNotFound.hide()
                        it.hide()
                    }
                }else{
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
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

    private fun observeEventSearch(){
        adapter = SearchEventAdapter()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvEventSearch.let{ rvEvent ->
            rvEvent.layoutManager = layoutManager
            rvEvent.adapter = adapter
        }

        mainViewModel.eventSearch.observe(this){
            binding.tvNotFound.hide()
            when(it){
                is Result.Loading -> {
                    showLoading()
                }
                is Result.Success -> {
                    hideLoading()

                    if(it.data.size > 0){
                        adapter.submitList(it.data)
                    }else{
                        adapter.submitList(null)
                        binding.tvNotFound.show()
                    }
                }
                is Result.Error -> {
                    hideLoading()
                    Util.showToast(this, it.error)
                }
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