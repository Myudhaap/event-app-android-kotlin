package dev.mayutama.project.eventapp.data.repository

import dev.mayutama.project.eventapp.data.local.dao.EventDao
import dev.mayutama.project.eventapp.data.remote.service.EventService

class EventRepository(
    private val eventService: EventService,
    private val eventDao: EventDao,
) {

    companion object{
        @Volatile
        private var INSTANCE: EventRepository? = null

        fun getInstance(
            eventService: EventService,
            eventDao: EventDao
        ): EventRepository {
            return INSTANCE ?: synchronized(this){
                INSTANCE = EventRepository(eventService, eventDao)
                INSTANCE as EventRepository
            }
        }
    }
}