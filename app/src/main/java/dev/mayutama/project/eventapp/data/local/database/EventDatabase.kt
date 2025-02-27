package dev.mayutama.project.eventapp.data.local.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.mayutama.project.eventapp.data.local.dao.EventDao
import dev.mayutama.project.eventapp.data.local.entity.EventEntity
import dev.mayutama.project.eventapp.data.local.entity.EventFavoriteEntity

@Database(
    entities = [
        EventEntity::class,
        EventFavoriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class EventDatabase: RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: EventDatabase? = null

        fun getInstance(application: Application): EventDatabase {
            return INSTANCE ?: synchronized(this){
                INSTANCE = Room.databaseBuilder(
                    application,
                    EventDatabase::class.java,
                    "db_event"
                ).build()

                return INSTANCE as EventDatabase
            }
        }
    }
}