package dev.mayutama.project.eventapp.ui.eventDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dev.mayutama.project.eventapp.data.remote.response.ListEventsItem
import dev.mayutama.project.eventapp.data.repository.EventFavoriteRepository
import dev.mayutama.project.eventapp.data.repository.EventRepository
import kotlinx.coroutines.launch
import dev.mayutama.project.eventapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventDetailViewModel(
    private val eventRepository: EventRepository,
    private val eventFavoriteRepository: EventFavoriteRepository
): ViewModel() {
    private val _event = MutableLiveData<Result<ListEventsItem>>()
    val event: LiveData<Result<ListEventsItem>> get() = _event

    private val _isFavorite = MutableLiveData<Result<Boolean>>()
    val isFavorite: LiveData<Result<Boolean>> get() = _isFavorite

    fun getEventById(id: Int){
        viewModelScope.launch {
            eventRepository.getEventById(id).asFlow().collect{
                _event.postValue(it)
            }
        }
    }

    fun getIsEventFavorite(id: Int) {
        viewModelScope.launch {
            eventFavoriteRepository.checkEventFavorite(id).observeForever {
                _isFavorite.postValue(it)
            }
        }
    }

    fun setFavorite(data: ListEventsItem){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                eventFavoriteRepository.addEventFavorite(data)
            }

            getIsEventFavorite(data.id!!)
        }
    }

    fun unsetFavorite(data: ListEventsItem){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                eventFavoriteRepository.deleteEventFavorite(data)
            }

            getIsEventFavorite(data.id!!)
        }
    }
}