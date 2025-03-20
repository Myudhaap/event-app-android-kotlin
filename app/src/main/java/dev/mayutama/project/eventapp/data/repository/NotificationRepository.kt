package dev.mayutama.project.eventapp.data.repository

import dev.mayutama.project.eventapp.data.local.dao.NotificationDao
import dev.mayutama.project.eventapp.data.local.entity.NotificationEntity
import dev.mayutama.project.eventapp.data.remote.response.ListEventsItem
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

    companion object {
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