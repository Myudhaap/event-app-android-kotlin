package dev.mayutama.project.eventapp.ui.main.finished

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.mayutama.project.eventapp.data.repository.EventRepository
import dev.mayutama.project.eventapp.di.Injection

class FinishedViewModelFactory private constructor(private val eventRepository: EventRepository):
    ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FinishedViewModel::class.java)){
            return FinishedViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown class ViewModel: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: FinishedViewModelFactory? = null

        fun getInstance(application: Application): FinishedViewModelFactory {
            return INSTANCE ?: synchronized(this){
                INSTANCE = FinishedViewModelFactory(Injection.provideEventRepository())
                INSTANCE as FinishedViewModelFactory
            }
        }
    }
}