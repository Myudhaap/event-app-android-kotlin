package dev.mayutama.project.eventapp.ui.main.setting

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import dev.mayutama.project.eventapp.R
import dev.mayutama.project.eventapp.config.RetrofitConfig
import dev.mayutama.project.eventapp.data.remote.response.ListEventsItem
import dev.mayutama.project.eventapp.data.remote.service.EventService
import dev.mayutama.project.eventapp.data.repository.NotificationRepository
import dev.mayutama.project.eventapp.di.Injection
import dev.mayutama.project.eventapp.ui.eventDetail.EventDetailActivity

class WorkerEventNotification(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams) {
    private var resultStatus: Result? = null
    private val service: EventService = RetrofitConfig.eventService
    private val notificationRepository: NotificationRepository = Injection.provideNotificationRepository(applicationContext as Application)

    override suspend fun doWork(): Result {
        return getEvent()
    }

    private suspend fun getEvent(): Result {
        Log.d(TAG, "getEvent: Start...")

        try{
            val response = service.getEventUpcoming()
            val events = response.listEvents
            if(events?.size == 0){
                Log.d(TAG, "getEvent: Event Not Found")
                showNotification("Event Notification", "Upcoming Event not found")
            }else{
                Log.d(TAG, "getEvent: ${events?.get(0)}")
                val bitmap = getBitmapImage(events?.get(0)?.imageLogo!!)
                val notificationId = notificationRepository.addNotification(events[0])
                showNotification(events[0].name!!, events[0].summary, bitmap, events[0], notificationId)
            }

            resultStatus = Result.success()
        }catch (e: Exception){
            Log.d(TAG, "getEvent: Error ${e.message}")
            resultStatus = Result.failure()
        }

        return resultStatus as Result
    }

    private fun showNotification(title: String, description: String?, bitmap: Bitmap? = null, event: ListEventsItem? = null, notificationId: Long? = null) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID).apply {
            setContentTitle(title)
            setContentText(description)
            setSmallIcon(R.drawable.baseline_circle_notifications_24)
            setPriority(NotificationCompat.PRIORITY_HIGH)
            setDefaults(NotificationCompat.DEFAULT_ALL)
            setAutoCancel(true)
            if(bitmap != null){
                setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            }
            if(event != null){
                val detailIntent = Intent(applicationContext, EventDetailActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra(EventDetailActivity.EXTRA_ID, event.id)
                    if(notificationId != null){
                        putExtra(EventDetailActivity.EXTRA_NOTIFICATION_ID, notificationId.toInt())
                    }
                }

                val pendingIntent = TaskStackBuilder.create(applicationContext).run {
                    addNextIntentWithParentStack(detailIntent)
                    getPendingIntent(NOTIFICATION_ID, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                }

                setContentIntent(pendingIntent)
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationBuilder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun getBitmapImage(imageUrl: String): Bitmap? {
        return try{
            Glide.with(applicationContext)
                .asBitmap()
                .load(imageUrl)
                .submit()
                .get()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    companion object {
        val TAG: String = WorkerEventNotification::class.java.simpleName
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_1"
        const val CHANNEL_NAME = "mayutama_channel"
    }
}