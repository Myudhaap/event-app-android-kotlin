package dev.mayutama.project.eventapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import dev.mayutama.project.eventapp.base.BaseDao
import dev.mayutama.project.eventapp.data.local.entity.EventFavoriteEntity

@Dao
interface EventFavoriteDao: BaseDao<EventFavoriteEntity> {
    @Query("""
        SELECT * FROM mst_event_favorite
        LIMIT :limit
    """)
    fun getAllEventFavorite(limit: Int = 50): LiveData<List<EventFavoriteEntity>>
}