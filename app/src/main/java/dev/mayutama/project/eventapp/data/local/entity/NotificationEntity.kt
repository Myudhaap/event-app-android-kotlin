package dev.mayutama.project.eventapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "mst_notification"
)
class NotificationEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey(autoGenerate = true)
    val id: Int,

    @field:ColumnInfo(name = "event_id")
    val eventId: Int,

    @field:ColumnInfo(name = "media_cover")
    val mediaCover: String,

    @field:ColumnInfo(name = "summary")
    val summary: String,

    @field:ColumnInfo(name = "description")
    val description: String,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "is_open")
    val isOpen: Boolean,

    @field:ColumnInfo(name = "created_at")
    val createdAt: String
)