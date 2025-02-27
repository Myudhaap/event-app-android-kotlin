package dev.mayutama.project.eventapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "mst_event"
)
class EventEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey()
    val id: Int,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "summary")
    val summary: String,

    @field:ColumnInfo(name = "description")
    val description: String,

    @field:ColumnInfo(name = "image_logo")
    val imageLogo: String,

    @field:ColumnInfo(name = "media_cover")
    val mediaCover: String,

    @field:ColumnInfo(name = "category")
    val category: String,

    @field:ColumnInfo(name = "owner_name")
    val ownerName: String,

    @field:ColumnInfo(name = "city_name")
    val citiName: String,

    @field:ColumnInfo(name = "quota")
    val quota: Int,

    @field:ColumnInfo(name = "registrans")
    val registrans: Int,

    @field:ColumnInfo(name = "begin_time")
    val beginTime: String,

    @field:ColumnInfo(name = "end_time")
    val endTime: String,

    @field:ColumnInfo(name = "link")
    val link: String
)