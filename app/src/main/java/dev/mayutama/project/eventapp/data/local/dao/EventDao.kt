package dev.mayutama.project.eventapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.mayutama.project.eventapp.base.BaseDao
import dev.mayutama.project.eventapp.data.local.entity.EventEntity
import dev.mayutama.project.eventapp.data.local.entity.EventFavoriteEntity

@Dao
interface EventDao: BaseDao<EventEntity> {
    @Query("""
        SELECT e.* FROM mst_event e
        JOIN mst_event_favorite ef ON e.id = ef.id
        LIMIT :limit
    """)
    fun getAllEventFavorite(limit: Int = 50): LiveData<List<EventEntity>>

    @Query("""
        SELECT * FROM mst_event
        LIMIT :limit
    """)
    fun getAllEvent(limit: Int = 50): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventFavorite(data: EventFavoriteEntity)

    @Delete
    suspend fun deleteEventFavorite(data: EventFavoriteEntity)
}