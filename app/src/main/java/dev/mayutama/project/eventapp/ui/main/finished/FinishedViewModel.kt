package dev.mayutama.project.eventapp.ui.main.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mayutama.project.eventapp.data.remote.response.ListEventsItem
import dev.mayutama.project.eventapp.data.repository.EventRepository
import dev.mayutama.project.eventapp.util.Result
import kotlinx.coroutines.launch

class FinishedViewModel(
    private val eventRepository: EventRepository
): ViewModel() {
    private val _listFinishedEvent = MutableLiveData<Result<List<ListEventsItem>>>()
    val listFinishedEvent: LiveData<Result<List<ListEventsItem>>> = _listFinishedEvent

    init{
        getAllFinishedEvent()
    }

    private fun getAllFinishedEvent(
        active: String = "0",
        q: String? = null,
        limit: Int = 50
    ) {
        viewModelScope.launch {
            eventRepository.getAllEvent(active, limit, q).observeForever {
                _listFinishedEvent.postValue(it)
            }
        }
    }
}