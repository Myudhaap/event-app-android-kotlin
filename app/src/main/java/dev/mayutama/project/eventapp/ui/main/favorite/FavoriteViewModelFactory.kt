package dev.mayutama.project.eventapp.ui.main.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.mayutama.project.eventapp.data.repository.EventFavoriteRepository
import dev.mayutama.project.eventapp.di.Injection

class FavoriteViewModelFactory private constructor(private val eventFavoriteRepository: EventFavoriteRepository):
    ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(eventFavoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown class ViewModel: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: FavoriteViewModelFactory? = null

        fun getInstance(application: Application): FavoriteViewModelFactory {
            return INSTANCE ?: synchronized(this){
                INSTANCE = FavoriteViewModelFactory(Injection.provideEventFavoriteRepository(application))
                INSTANCE as FavoriteViewModelFactory
            }
        }
    }
}