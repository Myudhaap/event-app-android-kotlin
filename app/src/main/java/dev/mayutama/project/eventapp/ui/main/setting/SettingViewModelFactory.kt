package dev.mayutama.project.eventapp.ui.main.setting

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.mayutama.project.eventapp.config.DataStoreConfig
import dev.mayutama.project.eventapp.di.Injection

class SettingViewModelFactory private constructor(private val dataStorePreferences: DataStoreConfig):
    ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SettingViewModel::class.java)){
            return SettingViewModel(dataStorePreferences) as T
        }
        throw IllegalArgumentException("Unknown class ViewModel: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingViewModelFactory? = null

        fun getInstance(application: Application): SettingViewModelFactory {
            return INSTANCE ?: synchronized(this){
                INSTANCE = SettingViewModelFactory(Injection.provideDataStore(application))
                INSTANCE as SettingViewModelFactory
            }
        }
    }
}