package dev.mayutama.project.eventapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "mst_event_favorite"
)
class EventFavoriteEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val eventId: Int,

    @field:ColumnInfo(name = "media_cover")
    val mediaCover: String,

    @field:ColumnInfo(name = "summary")
    val summary: String,

    @field:ColumnInfo(name = "description")
    val description: String,

    @field:ColumnInfo(name = "name")
    val name: String,
)