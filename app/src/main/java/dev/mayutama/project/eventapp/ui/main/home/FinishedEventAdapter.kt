package dev.mayutama.project.eventapp.ui.main.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.mayutama.project.eventapp.R
import dev.mayutama.project.eventapp.data.remote.response.ListEventsItem
import dev.mayutama.project.eventapp.databinding.ItemFinishedHomeBinding
import dev.mayutama.project.eventapp.ui.eventDetail.EventDetailActivity

class FinishedEventAdapter:
    ListAdapter<ListEventsItem, RecyclerView.ViewHolder>(DIFF_CALLBACK)
{
    inner class FinishedViewHolder(private val binding: ItemFinishedHomeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ListEventsItem){
            Glide.with(binding.root.context)
                .load(item.mediaCover)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(binding.imgBanner)

            binding.tvBannerTitle.text = item.name
            binding.tvBannerDescription.text = item.summary

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, EventDetailActivity::class.java).apply {
                    putExtra(EventDetailActivity.EXTRA_ID, item.id)
                }

                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemFinishedHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinishedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currItem = getItem(position)
        if(holder is FinishedViewHolder) {
            holder.bind(currItem)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<ListEventsItem>(){
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}