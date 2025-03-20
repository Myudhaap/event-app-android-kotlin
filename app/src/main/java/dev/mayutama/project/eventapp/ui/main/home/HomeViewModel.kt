package dev.mayutama.project.eventapp.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dev.mayutama.project.eventapp.data.remote.response.ListEventsItem
import dev.mayutama.project.eventapp.data.repository.EventRepository
import dev.mayutama.project.eventapp.util.Result
import kotlinx.coroutines.launch

@Suppress("SameParameterValue", "SameParameterValue")
class HomeViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {
    private val _listEventUpcoming = MutableLiveData<Result<List<ListEventsItem>>>()
    val listEventUpcoming: LiveData<Result<List<ListEventsItem>>> = _listEventUpcoming

    private val _listEventFinished = MutableLiveData<Result<List<ListEventsItem>>>()
    val listEventFinished: LiveData<Result<List<ListEventsItem>>> = _listEventFinished

    init{
        getAllEventUpcoming(limit = 5)
        getAllEventFinished(limit = 5)
    }

    private fun getAllEventUpcoming(
        active: String? = "1",
        q: String? = null,
        limit: Int? = null
    ) {
        viewModelScope.launch {
            eventRepository.getAllEvent(active, limit, q).asFlow().collect{
                _listEventUpcoming.postValue(it)
            }
        }
    }

    private fun getAllEventFinished(
        active: String? = "0",
        q: String? = null,
        limit: Int? = null
    ) {
        viewModelScope.launch {
            eventRepository.getAllEvent(active, limit, q).asFlow().collect{
                _listEventFinished.postValue(it)
            }
        }
    }
}