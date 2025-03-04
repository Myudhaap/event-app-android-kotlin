package dev.mayutama.project.eventapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import dev.mayutama.project.eventapp.base.BaseDao
import dev.mayutama.project.eventapp.data.local.entity.NotificationEntity

@Dao
interface NotificationDao: BaseDao<NotificationEntity> {
    @Query("""
        SELECT * FROM mst_notification
    """)
    fun getAllNotification(): LiveData<List<NotificationEntity>>
}