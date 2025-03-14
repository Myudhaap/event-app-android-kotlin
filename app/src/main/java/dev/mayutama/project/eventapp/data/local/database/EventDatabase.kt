package dev.mayutama.project.eventapp.data.local.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.mayutama.project.eventapp.data.local.dao.EventFavoriteDao
import dev.mayutama.project.eventapp.data.local.dao.NotificationDao
import dev.mayutama.project.eventapp.data.local.entity.EventFavoriteEntity
import dev.mayutama.project.eventapp.data.local.entity.NotificationEntity

@Database(
    entities = [
        EventFavoriteEntity::class,
        NotificationEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class EventDatabase: RoomDatabase() {
    abstract fun eventFavoriteDao(): EventFavoriteDao
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: EventDatabase? = null

        fun getInstance(application: Application): EventDatabase {
            return INSTANCE ?: synchronized(this){
                INSTANCE = Room.databaseBuilder(
                    application,
                    EventDatabase::class.java,
                    "db_event"
                ).fallbackToDestructiveMigration().build()

                return INSTANCE as EventDatabase
            }
        }
    }
}