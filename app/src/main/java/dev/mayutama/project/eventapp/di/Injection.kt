package dev.mayutama.project.eventapp.di

import android.app.Application
import dev.mayutama.project.eventapp.config.DataStoreConfig
import dev.mayutama.project.eventapp.config.RetrofitConfig
import dev.mayutama.project.eventapp.config.dataStore
import dev.mayutama.project.eventapp.data.local.dao.EventFavoriteDao
import dev.mayutama.project.eventapp.data.local.database.EventDatabase
import dev.mayutama.project.eventapp.data.remote.service.EventService
import dev.mayutama.project.eventapp.data.repository.EventFavoriteRepository
import dev.mayutama.project.eventapp.data.repository.EventRepository

object Injection {
    fun provideDataStore(application: Application): DataStoreConfig {
        return DataStoreConfig.getInstance(application.dataStore)
    }

    fun provideEventRepository(application: Application): EventRepository {
        val eventService: EventService = RetrofitConfig.eventService
        return EventRepository(eventService)
    }

    fun provideEventFavoriteRepository(application: Application): EventFavoriteRepository {
        val eventService: EventService = RetrofitConfig.eventService
        val database: EventDatabase = EventDatabase.getInstance(application)
        val eventFavoriteDao: EventFavoriteDao = database.eventFavoriteDao()
        return EventFavoriteRepository.getInstance(eventService, eventFavoriteDao)
    }
}