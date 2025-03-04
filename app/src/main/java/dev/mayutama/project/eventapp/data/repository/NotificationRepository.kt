package dev.mayutama.project.eventapp.data.repository

import dev.mayutama.project.eventapp.data.local.dao.NotificationDao

class NotificationRepository(
    private val notificationDao: NotificationDao
) {

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