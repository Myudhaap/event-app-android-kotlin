package dev.mayutama.project.eventapp.ui.main.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mayutama.project.eventapp.data.remote.response.ListEventsItem
import dev.mayutama.project.eventapp.data.repository.EventFavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val eventFavoriteRepository: EventFavoriteRepository
): ViewModel() {

    fun getEventFavoriteList() = eventFavoriteRepository.getEventFavorite()

    fun saveEventFavorite(data: ListEventsItem) {
        viewModelScope.launch {
            eventFavoriteRepository.addEventFavorite(data)
        }
    }

    fun deleteEventFavorite(data: ListEventsItem) {
        viewModelScope.launch {
            eventFavoriteRepository.deleteEventFavorite(data)
        }
    }
}