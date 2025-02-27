package dev.mayutama.project.eventapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "mst_event_favorite"
)
class EventFavoriteEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey(autoGenerate = true)
    val id: Int,

    @field:ColumnInfo(name = "event_id")
    val eventId: Int
)