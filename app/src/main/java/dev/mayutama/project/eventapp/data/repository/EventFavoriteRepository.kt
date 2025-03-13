package dev.mayutama.project.eventapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import dev.mayutama.project.eventapp.data.local.dao.EventFavoriteDao
import dev.mayutama.project.eventapp.data.local.entity.EventFavoriteEntity
import dev.mayutama.project.eventapp.data.remote.response.ListEventsItem
import dev.mayutama.project.eventapp.data.remote.service.EventService
import dev.mayutama.project.eventapp.util.Result

class EventFavoriteRepository private constructor(
    private val eventService: EventService,
    private val eventFavoriteDao: EventFavoriteDao
) {
    fun getEventFavorite(): LiveData<Result<List<EventFavoriteEntity>>> = liveData {
        emit(Result.Loading)

        try{
            val queryMap: Map<String, String> = mapOf(
                "active" to "-1"
            )
            val response = eventService.getAllEvent(queryMap)
            val events = response.listEvents
            val eventFavoriteList: List<EventFavoriteEntity>? = events?.filter { event ->
                val isFavoriteEvent = eventFavoriteDao.isFavoriteEvent(event.id!!)
                isFavoriteEvent
            }?.map { event ->
                EventFavoriteEntity(
                    null,
                    event.id!!,
                    event.mediaCover!!,
                    event.summary!!,
                    event.description!!,
                    event.name!!
                )
            }

            eventFavoriteDao.deleteAll()
            eventFavoriteDao.insertAll(eventFavoriteList!!)
        }catch (e: Exception){
            Log.d("EventFavoriteRepository", "getEventFavorite: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }

        val localData: LiveData<Result<List<EventFavoriteEntity>>> = eventFavoriteDao.getAllEventFavorite().map { events: List<EventFavoriteEntity> ->
            Result.Success(events)
        }

        emitSource(localData)
    }

    suspend fun addEventFavorite(data: ListEventsItem){
        eventFavoriteDao.insert(EventFavoriteEntity(
            null,
            data.id!!,
            data.mediaCover!!,
            data.summary!!,
            data.description!!,
            data.name!!
        ))
    }

    suspend fun deleteEventFavorite(data: ListEventsItem){
        val event = eventFavoriteDao.getById(data.id!!)
        eventFavoriteDao.delete(event)
    }

    companion object {
        @Volatile
        private var INSTANCE: EventFavoriteRepository? = null

        fun getInstance(
            eventService: EventService,
            eventFavoriteDao: EventFavoriteDao
        ): EventFavoriteRepository {
            return INSTANCE ?: synchronized(this){
                INSTANCE = EventFavoriteRepository(eventService, eventFavoriteDao)
                INSTANCE as EventFavoriteRepository
            }
        }
    }
}