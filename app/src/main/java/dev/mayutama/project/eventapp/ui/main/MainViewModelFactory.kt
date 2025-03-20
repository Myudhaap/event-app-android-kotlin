package dev.mayutama.project.eventapp.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.mayutama.project.eventapp.data.repository.EventRepository
import dev.mayutama.project.eventapp.di.Injection

class MainViewModelFactory private constructor(
    private val eventRepository: EventRepository
):
    ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(eventRepository) as T
        }

        throw IllegalArgumentException("Unknown class ViewModel: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: MainViewModelFactory? = null

        fun getInstance(
            application: Application
        ): MainViewModelFactory {
            return INSTANCE ?: synchronized(this){
                INSTANCE = MainViewModelFactory(Injection.provideEventRepository(application))
                INSTANCE as MainViewModelFactory
            }
        }
    }
}