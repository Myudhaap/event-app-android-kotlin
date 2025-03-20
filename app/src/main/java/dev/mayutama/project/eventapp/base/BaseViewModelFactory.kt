package dev.mayutama.project.eventapp.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.mayutama.project.eventapp.config.DataStoreConfig
import dev.mayutama.project.eventapp.di.Injection

class BaseViewModelFactory private constructor(
    private val dataStoreConfig: DataStoreConfig
):
    ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BaseViewModel::class.java)){
            return BaseViewModel(dataStoreConfig) as T
        }

        throw IllegalArgumentException("Unknown class ViewModel: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: BaseViewModelFactory? = null

        fun getInstance(
            application: Application
        ): BaseViewModelFactory {
            return INSTANCE ?: synchronized(this){
                INSTANCE = BaseViewModelFactory(Injection.provideDataStore(application))
                INSTANCE as BaseViewModelFactory
            }
        }
    }
}