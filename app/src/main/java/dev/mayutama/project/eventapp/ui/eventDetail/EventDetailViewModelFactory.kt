package dev.mayutama.project.eventapp.ui.eventDetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.mayutama.project.eventapp.data.repository.EventFavoriteRepository
import dev.mayutama.project.eventapp.data.repository.EventRepository
import dev.mayutama.project.eventapp.data.repository.NotificationRepository
import dev.mayutama.project.eventapp.di.Injection

class EventDetailViewModelFactory private constructor(private val eventRepository: EventRepository, private val eventFavoriteRepository: EventFavoriteRepository, private val notificationRepository: NotificationRepository):
    ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EventDetailViewModel::class.java)){
            return EventDetailViewModel(eventRepository, eventFavoriteRepository, notificationRepository) as T
        }
        throw IllegalArgumentException("Unknown class ViewModel: ${modelClass.name}")
    }

    companion object {
            @Volatile
            private var INSTANCE: EventDetailViewModelFactory? = null

            fun getInstance(application: Application): EventDetailViewModelFactory {
                return INSTANCE ?: synchronized(this){
                    INSTANCE = EventDetailViewModelFactory(Injection.provideEventRepository(), Injection.provideEventFavoriteRepository(application), Injection.provideNotificationRepository(application))
                    INSTANCE as EventDetailViewModelFactory
                }
            }
        }
}