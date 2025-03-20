package dev.mayutama.project.eventapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import dev.mayutama.project.eventapp.data.local.dao.NotificationDao
import dev.mayutama.project.eventapp.data.local.entity.NotificationEntity
import dev.mayutama.project.eventapp.data.remote.response.ListEventsItem
import dev.mayutama.project.eventapp.util.Result
import java.util.Calendar

class NotificationRepository(
    private val notificationDao: NotificationDao
) {

    suspend fun addNotification(event: ListEventsItem): Long{
        val notificationEntity = NotificationEntity(
            eventId = event.id!!,
            mediaCover = event.mediaCover!!,
            summary = event.summary!!,
            description = event.description!!,
            name = event.name!!,
            isOpen = false,
            createdAt = Calendar.getInstance().time.toString()
        )

        return notificationDao.insertNotification(notificationEntity)
    }

    suspend fun updateNotification(id: Int){
        val currentNotification = notificationDao.getNotificationById(id)
        currentNotification.isOpen = true

        notificationDao.update(currentNotification)
    }

    fun getNotificationCount(): LiveData<Result<Int>> = liveData {
        emit(Result.Loading)

        try{
            val notificationCount: LiveData<Result<Int>> = notificationDao.getCountNotification().map { count: Int ->
                Result.Success(count)
            }
            emitSource(notificationCount)
        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
            Log.d(TAG, "getNotificationCount: ${e.message.toString()}")
        }
    }

    companion object {
        private var TAG: String = NotificationRepository::class.java.simpleName
        private var INSTANCE: NotificationRepository? = null

        fun getInstance(
            notificationDao: NotificationDao
        ): NotificationRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = NotificationRepository(notificationDao)
                INSTANCE as NotificationRepository
            }
        }
    }
}