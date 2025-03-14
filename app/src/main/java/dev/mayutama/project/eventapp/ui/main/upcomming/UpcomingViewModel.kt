package dev.mayutama.project.eventapp.ui.main.upcomming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dev.mayutama.project.eventapp.data.remote.response.ListEventsItem
import dev.mayutama.project.eventapp.data.repository.EventRepository
import kotlinx.coroutines.launch
import dev.mayutama.project.eventapp.util.Result

class UpcomingViewModel(
    private val eventRepository: EventRepository
): ViewModel() {
    private val _listUpcomingEvent = MutableLiveData<Result<List<ListEventsItem>>>()
    val listUpcomingEvent: LiveData<Result<List<ListEventsItem>>> = _listUpcomingEvent

    init {
        getAllEventUpcoming()
    }

    private fun getAllEventUpcoming(
        limit: Int = 50
    ) {
        viewModelScope.launch {
            eventRepository.getAllEvent("1", limit, null).asFlow().collect{
                _listUpcomingEvent.postValue(it)
            }
        }
    }
}