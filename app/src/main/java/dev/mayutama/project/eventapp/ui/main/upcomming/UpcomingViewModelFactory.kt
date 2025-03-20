package dev.mayutama.project.eventapp.ui.main.upcomming

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.mayutama.project.eventapp.data.repository.EventRepository
import dev.mayutama.project.eventapp.di.Injection

class UpcomingViewModelFactory private constructor(private val eventRepository: EventRepository):
    ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UpcomingViewModel::class.java)){
            return UpcomingViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown class ViewModel: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: UpcomingViewModelFactory? = null

        fun getInstance(
            application: Application
        ): UpcomingViewModelFactory {
            return INSTANCE ?: synchronized(this){
                INSTANCE = UpcomingViewModelFactory(Injection.provideEventRepository())
                INSTANCE as UpcomingViewModelFactory
            }
        }
    }
}