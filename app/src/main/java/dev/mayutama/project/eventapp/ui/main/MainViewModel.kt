package dev.mayutama.project.eventapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dev.mayutama.project.eventapp.config.DataStoreConfig

class MainViewModel(
    private val dataStorePreference: DataStoreConfig
): ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> {
        return dataStorePreference.getThemeSetting().asLiveData()
    }
}