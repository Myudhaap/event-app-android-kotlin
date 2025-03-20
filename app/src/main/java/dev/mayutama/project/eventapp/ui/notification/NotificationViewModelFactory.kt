package dev.mayutama.project.eventapp.ui.notification

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.mayutama.project.eventapp.data.repository.NotificationRepository
import dev.mayutama.project.eventapp.di.Injection

class NotificationViewModelFactory private constructor(
    private val notificationRepository: NotificationRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotificationViewModel::class.java)){
                return NotificationViewModel(notificationRepository) as T
        }
        throw IllegalArgumentException("Unknown class ViewModel: ${modelClass.name}")
    }

    companion object{
        private var INSTANCE: NotificationViewModelFactory? = null

        fun getInstance(application: Application): NotificationViewModelFactory {
            return INSTANCE ?: synchronized(this){
                INSTANCE = NotificationViewModelFactory(Injection.provideNotificationRepository(application))
                INSTANCE as NotificationViewModelFactory
            }
        }
    }
}