package dev.mayutama.project.eventapp.util

import android.content.Context
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

object Util {
    fun enableScreenAction(window: Window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun disableScreenAction(window: Window) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun showToast(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(view: View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun getTimeAgo(createdAt: String): String{
        val now = Calendar.getInstance().timeInMillis
        Log.d("Util", "getTimeAgo: Now $now")

        try{
            val format = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
            Log.d("Util", "getTimeAgo: Format $format")

            val date = format.parse(createdAt)
            Log.d("Util", "getTimeAgo: Date $date")

            val created = Calendar.getInstance().apply {
                if (date != null) {
                    time = date
                }
            }.timeInMillis
            Log.d("Util", "getTimeAgo: CreatedAt $created")

            val diff = now - created
            Log.d("Util", "getTimeAgo: Diff $diff")

            return when {
                diff < TimeUnit.MINUTES.toMillis(1) -> "Just now"
                diff < TimeUnit.MINUTES.toMillis(2) -> "A minute ago"
                diff < TimeUnit.HOURS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toMinutes(diff)} minutes ago"
                diff < TimeUnit.HOURS.toMillis(2) -> "An hour ago"
                diff < TimeUnit.DAYS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toHours(diff)} hours ago"
                diff < TimeUnit.DAYS.toMillis(2) -> "Yesterday"
                else -> "${TimeUnit.MILLISECONDS.toDays(diff)} days ago"
            }
        }catch (e: Exception){
            e.printStackTrace()
            return "-"
        }
    }
}