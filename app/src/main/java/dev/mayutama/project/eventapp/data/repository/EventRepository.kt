package dev.mayutama.project.eventapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import dev.mayutama.project.eventapp.data.local.dao.EventFavoriteDao
import dev.mayutama.project.eventapp.data.remote.response.ListEventsItem
import dev.mayutama.project.eventapp.data.remote.service.EventService
import dev.mayutama.project.eventapp.util.Result

class EventRepository(
    private val eventService: EventService,
) {
    fun getAllEvent(
        active: String?,
        limit: Int?,
        q: String?
    ): LiveData<Result<List<ListEventsItem>>> = liveData {
        emit(Result.Loading)

        try{
            val queryMap: Map<String, String> = mapOf(
                "active" to active,
                "q" to q,
                "limit" to limit
            ).filterValues {
                it != null
            }.mapValues { it.value.toString() }

            val response = eventService.getAllEvent(queryMap)
            val events = response.listEvents!!
            emit(Result.Success(events))
        }catch (e: Exception){
            Log.d(TAG, "getAllEvent: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: EventRepository? = null
        private var TAG: String = EventRepository::class.java.simpleName

        fun getInstance(
            eventService: EventService,
        ): EventRepository {
            return INSTANCE ?: synchronized(this){
                INSTANCE = EventRepository(eventService)
                INSTANCE as EventRepository
            }
        }
    }
}