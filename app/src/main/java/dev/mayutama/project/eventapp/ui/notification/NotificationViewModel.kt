package dev.mayutama.project.eventapp.ui.notification

import androidx.lifecycle.ViewModel
import dev.mayutama.project.eventapp.data.repository.NotificationRepository

class NotificationViewModel(
    private val notificationRepository: NotificationRepository
): ViewModel() {
    fun getNotificationList() = notificationRepository.getAllNotification()
}