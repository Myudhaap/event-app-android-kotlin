package dev.mayutama.project.eventapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.mayutama.project.eventapp.base.BaseDao
import dev.mayutama.project.eventapp.data.local.entity.NotificationEntity

@Dao
interface NotificationDao: BaseDao<NotificationEntity> {
    @Query("""
        SELECT * FROM mst_notification ORDER BY created_at DESC
    """)
    fun getAllNotification(): LiveData<List<NotificationEntity>>

    @Query(
        """
        SELECT COUNT(id) FROM mst_notification
        WHERE is_open = 0
    """
    )
    fun getCountNotification(): LiveData<Int>

    @Query("""
        SELECT * FROM mst_notification WHERE id = :id
    """)
    suspend fun getNotificationById(id: Int): NotificationEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotification(data: NotificationEntity): Long
}