package dev.mayutama.project.eventapp.ui.main.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.mayutama.project.eventapp.config.DataStoreConfig
import kotlinx.coroutines.launch

class SettingViewModel(
    private val dataStorePreferences: DataStoreConfig
): ViewModel() {
    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            dataStorePreferences.saveThemeSetting(isDarkMode)
        }
    }

    fun getThemeSetting(): LiveData<Boolean> {
        return dataStorePreferences.getThemeSetting().asLiveData()
    }

    fun saveDailyReminderSetting(isReminder: Boolean) {
        viewModelScope.launch {
            dataStorePreferences.saveReminderSetting(isReminder)
        }
    }
}