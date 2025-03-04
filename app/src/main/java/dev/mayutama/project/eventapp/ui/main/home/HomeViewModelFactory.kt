package dev.mayutama.project.eventapp.ui.main.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.mayutama.project.eventapp.data.repository.EventRepository
import dev.mayutama.project.eventapp.di.Injection

class HomeViewModelFactory private constructor(private val eventRepository: EventRepository):
    ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(eventRepository) as T
        }

        throw IllegalArgumentException("Unknown class ViewModel: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: HomeViewModelFactory? = null

        fun getInstance(application: Application): HomeViewModelFactory {
            return INSTANCE ?: synchronized(this){
                INSTANCE = HomeViewModelFactory(Injection.provideEventRepository(application))
                INSTANCE as HomeViewModelFactory
            }
        }
    }
}