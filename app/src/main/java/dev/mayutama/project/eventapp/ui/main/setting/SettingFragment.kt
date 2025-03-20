package dev.mayutama.project.eventapp.ui.main.setting

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.material.materialswitch.MaterialSwitch
import dev.mayutama.project.eventapp.base.BaseFragment
import dev.mayutama.project.eventapp.databinding.FragmentSettingBinding
import java.util.Calendar
import java.util.concurrent.TimeUnit

class SettingFragment :
    BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate)
{
    private lateinit var workManager: WorkManager

    private val settingViewModel: SettingViewModel by viewModels {
        SettingViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProperties()
        init()
        initListener()
    }

    private fun initProperties(){
        workManager = WorkManager.getInstance(requireContext())
    }

    private fun init(){
        checkTheme()
        checkDailyReminder()
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

            if(b){
                Log.d(TAG, "onChangeDailyReminder: true")
                scheduleDailyReminder()
            }else{
                Log.d(TAG, "onChangeDailyReminder: false")
                workManager.cancelUniqueWork(UNIQUE_WORK_NAME)
                workManager.pruneWork()
            }
        }
    }

    private fun checkTheme() {
        settingViewModel.getThemeSetting().observe(viewLifecycleOwner){
            binding.msDarkMode.isChecked = it
        }
    }

    private fun checkDailyReminder() {
        settingViewModel.getDailyReminderSetting().observe(viewLifecycleOwner){
            binding.msDailyReminder.isChecked = it
        }
    }

    private fun scheduleDailyReminder(){
        workManager.cancelUniqueWork(UNIQUE_WORK_NAME)
        workManager.pruneWork()

        val currentTime = Calendar.getInstance()
        val targetTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 15)
            set(Calendar.MINUTE, 25)
            set(Calendar.SECOND, 0)
        }

        if(currentTime.after(targetTime)){
            targetTime.add(Calendar.DAY_OF_MONTH, 1)
        }

        val delay = targetTime.timeInMillis - currentTime.timeInMillis

        val workRequest = PeriodicWorkRequest.Builder(WorkerEventNotification::class, 1, TimeUnit.DAYS)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .addTag(WorkerEventNotification.TAG)
            .build()

        workManager.enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )

        Log.d(TAG, "scheduleDailyReminder: Observe")

        workManager.getWorkInfosForUniqueWorkLiveData(UNIQUE_WORK_NAME)
            .observe(viewLifecycleOwner){ workInfos ->
                for (workInfo in workInfos){
                    val status = workInfo.state
                    Log.d(TAG, "scheduleDailyReminder: $status")
                    if(status == WorkInfo.State.FAILED){
                        scheduleDailyReminder()
                    }
                }
            }
    }

    companion object{
        private val TAG: String = SettingFragment::class.java.simpleName
        private const val UNIQUE_WORK_NAME = "DailyReminderNotificationEventApp"
    }
}