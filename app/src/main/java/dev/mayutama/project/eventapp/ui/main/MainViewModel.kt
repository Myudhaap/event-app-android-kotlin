package dev.mayutama.project.eventapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dev.mayutama.project.eventapp.data.remote.response.ListEventsItem
import dev.mayutama.project.eventapp.data.repository.EventRepository
import kotlinx.coroutines.launch
import dev.mayutama.project.eventapp.util.Result

class MainViewModel(
    private val eventRepository: EventRepository
): ViewModel() {
    private val _eventSearch = MutableLiveData<Result<List<ListEventsItem>>>()
    val eventSearch: LiveData<Result<List<ListEventsItem>>> get() = _eventSearch

    fun getEventSearch(q: String){
        viewModelScope.launch {
            eventRepository.getAllEvent("-1", null, q).asFlow().collect{
                _eventSearch.postValue(it)
            }
        }
    }
}