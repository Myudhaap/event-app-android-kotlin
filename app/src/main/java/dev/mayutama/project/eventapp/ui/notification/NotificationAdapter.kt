package dev.mayutama.project.eventapp.ui.notification

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.mayutama.project.eventapp.R
import dev.mayutama.project.eventapp.data.local.entity.NotificationEntity
import dev.mayutama.project.eventapp.databinding.ItemNotificationBinding
import dev.mayutama.project.eventapp.ui.eventDetail.EventDetailActivity
import dev.mayutama.project.eventapp.util.Util
import dev.mayutama.project.eventapp.util.hide
import dev.mayutama.project.eventapp.util.show

class NotificationAdapter: ListAdapter<NotificationEntity, NotificationAdapter.NotificationViewHolder>(DIFF_UTIL) {
    inner class NotificationViewHolder(private val binding: ItemNotificationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(notification: NotificationEntity){
            binding.imgFlagNew.hide()
            binding.tvTitle.text = notification.name
            binding.tvDescription.text = notification.summary
            binding.tvLastTime.text = Util.getTimeAgo(notification.createdAt)
            if(!notification.isOpen){
                binding.imgFlagNew.show()
            }
            Glide.with(binding.root.context)
                .load(notification.mediaCover)
                .error(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .into(binding.imgNotification)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, EventDetailActivity::class.java).apply {
                    putExtra(EventDetailActivity.EXTRA_ID, notification.eventId)
                    if(!notification.isOpen){
                        putExtra(EventDetailActivity.EXTRA_NOTIFICATION_ID, notification.id)
                    }
                }

                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val currentNotification = getItem(position)
        holder.bind(currentNotification)
    }

    companion object{
        private val DIFF_UTIL: DiffUtil.ItemCallback<NotificationEntity> = object: DiffUtil.ItemCallback<NotificationEntity>(){
            override fun areItemsTheSame(
                oldItem: NotificationEntity,
                newItem: NotificationEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: NotificationEntity,
                newItem: NotificationEntity
            ): Boolean {
                return newItem == oldItem
            }
        }
    }
}