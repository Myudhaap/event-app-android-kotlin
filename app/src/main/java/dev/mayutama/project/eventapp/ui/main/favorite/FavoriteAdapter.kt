package dev.mayutama.project.eventapp.ui.main.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.mayutama.project.eventapp.R
import dev.mayutama.project.eventapp.data.local.entity.EventFavoriteEntity
import dev.mayutama.project.eventapp.databinding.ItemEventFavoriteBinding
import dev.mayutama.project.eventapp.ui.eventDetail.EventDetailActivity

class FavoriteAdapter: ListAdapter<EventFavoriteEntity, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {
    inner class FavoriteViewHolder(private val binding: ItemEventFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: EventFavoriteEntity){
            Glide.with(binding.root.context)
                .load(data.mediaCover)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(binding.imgBanner)

            binding.tvTitle.text = data.name
            binding.tvDescription.text = data.summary
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, EventDetailActivity::class.java).apply {
                    putExtra(EventDetailActivity.EXTRA_ID, data.eventId)
                }

                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemEventFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    companion object{
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<EventFavoriteEntity>(){
            override fun areItemsTheSame(
                oldItem: EventFavoriteEntity,
                newItem: EventFavoriteEntity
            ): Boolean {
                return oldItem.eventId == newItem.eventId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: EventFavoriteEntity,
                newItem: EventFavoriteEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}