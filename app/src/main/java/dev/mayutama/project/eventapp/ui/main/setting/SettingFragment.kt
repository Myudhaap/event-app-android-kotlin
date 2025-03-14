package dev.mayutama.project.eventapp.ui.main.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.materialswitch.MaterialSwitch
import dev.mayutama.project.eventapp.base.BaseFragment
import dev.mayutama.project.eventapp.databinding.FragmentSettingBinding

class SettingFragment :
    BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate)
{
    private val settingViewModel: SettingViewModel by viewModels {
        SettingViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initListener()
    }

    private fun init(){
        checkTheme()
    }

    private fun initListener(){
        onChangeDarkMode()
        onChangeDailyReminder()
    }

    private fun onChangeDarkMode(){
        val switchDarkMode: MaterialSwitch = binding.msDarkMode

        switchDarkMode.setOnCheckedChangeListener { _, b ->
            settingViewModel.saveThemeSetting(b)
        }
    }

    private fun onChangeDailyReminder(){
        val switchDailyReminder: MaterialSwitch = binding.msDailyReminder

        switchDailyReminder.setOnCheckedChangeListener { _, b ->
            settingViewModel.saveDailyReminderSetting(b)
        }
    }

    private fun checkTheme() {
        settingViewModel.getThemeSetting().observe(viewLifecycleOwner){
            binding.msDarkMode.isChecked = it
        }
    }
}