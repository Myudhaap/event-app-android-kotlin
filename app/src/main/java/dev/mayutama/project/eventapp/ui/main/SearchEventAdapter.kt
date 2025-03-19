package dev.mayutama.project.eventapp.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.mayutama.project.eventapp.R
import dev.mayutama.project.eventapp.data.remote.response.ListEventsItem
import dev.mayutama.project.eventapp.databinding.ItemEventSearchBinding
import dev.mayutama.project.eventapp.ui.eventDetail.EventDetailActivity

class SearchEventAdapter:
    ListAdapter<ListEventsItem, SearchEventAdapter.SearchEventViewHolder>(DIFF_CALL)
{
    inner class SearchEventViewHolder(private val binding: ItemEventSearchBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(event: ListEventsItem){
            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(binding.imgEvent)

            binding.tvTitle.text = event.name
            binding.tvSummary.text = event.summary

            binding.root.setOnClickListener{
                val intent = Intent(binding.root.context, EventDetailActivity::class.java).apply {
                    putExtra(EventDetailActivity.EXTRA_ID, event.id)
                }

                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchEventViewHolder {
        val binding = ItemEventSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchEventViewHolder, position: Int) {
        val currentEvent = getItem(position)
        holder.bind(currentEvent)
    }

    companion object {
        private val DIFF_CALL = object: DiffUtil.ItemCallback<ListEventsItem>(){
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}