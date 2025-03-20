package dev.mayutama.project.eventapp.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dev.mayutama.project.eventapp.config.DataStoreConfig

class BaseViewModel(
    private val dataStorePreference: DataStoreConfig
): ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> {
        return dataStorePreference.getThemeSetting().asLiveData()
    }
}