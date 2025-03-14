package dev.mayutama.project.eventapp.ui.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dev.mayutama.project.eventapp.data.local.entity.EventFavoriteEntity
import dev.mayutama.project.eventapp.data.repository.EventFavoriteRepository
import dev.mayutama.project.eventapp.util.Result
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val eventFavoriteRepository: EventFavoriteRepository
): ViewModel() {

    private val _eventFavoriteList = MutableLiveData<Result<List<EventFavoriteEntity>>>()
    val eventFavoriteList: LiveData<Result<List<EventFavoriteEntity>>> get() = _eventFavoriteList

    init {
        getEventFavoriteList()
    }

    private fun getEventFavoriteList(){
        viewModelScope.launch {
            eventFavoriteRepository.getEventFavorite().asFlow()
                .collect{
                    _eventFavoriteList.postValue(it)
                }
        }
    }
}