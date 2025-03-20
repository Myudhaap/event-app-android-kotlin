package dev.mayutama.project.eventapp.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.ViewBinding

open class BaseActivity<VB: ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> VB
): AppCompatActivity() {
    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!
    private val baseViewModel: BaseViewModel by viewModels {
        BaseViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        checkTheme()
        super.onCreate(savedInstanceState)

        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    private fun checkTheme() {
        baseViewModel.getThemeSetting().observe(this){
            if(it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}