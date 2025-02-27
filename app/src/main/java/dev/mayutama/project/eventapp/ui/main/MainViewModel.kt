package dev.mayutama.project.eventapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.mayutama.project.eventapp.config.DataStoreConfig
import dev.mayutama.project.eventapp.data.repository.EventRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val dataStorePreference: DataStoreConfig,
    private val eventRepository: EventRepository
): ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> {
        return dataStorePreference.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            dataStorePreference.saveThemeSetting(isDarkMode)
        }
    }
}