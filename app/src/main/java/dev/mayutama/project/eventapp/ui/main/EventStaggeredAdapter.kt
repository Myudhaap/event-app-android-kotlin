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
import dev.mayutama.project.eventapp.databinding.ItemEventType1Binding
import dev.mayutama.project.eventapp.databinding.ItemEventType2Binding
import dev.mayutama.project.eventapp.ui.eventDetail.EventDetailActivity

class EventStaggeredAdapter: ListAdapter<ListEventsItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    inner class UpcomingViewHolderType1(private val binding: ItemEventType1Binding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ListEventsItem){
            Glide.with(binding.root.context)
                .load(item.mediaCover)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(binding.imgUpcoming)

            binding.tvTitle.text = item.name.toString().trim()
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, EventDetailActivity::class.java).apply {
                    putExtra(EventDetailActivity.EXTRA_ID, item.id)
                }

                binding.root.context.startActivity(intent)
            }
        }
    }

    inner class UpcomingViewHolderType2(private val binding: ItemEventType2Binding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ListEventsItem){
            Glide.with(binding.root.context)
                .load(item.mediaCover)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(binding.imgUpcoming)

            binding.tvTitle.text = item.name.toString().trim()
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, EventDetailActivity::class.java).apply {
                    putExtra(EventDetailActivity.EXTRA_ID, item.id)
                }

                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 5 == 0) TYPE_2 else TYPE_1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_2){
            val binding = ItemEventType2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UpcomingViewHolderType2(binding)
        }else{
            val binding = ItemEventType1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UpcomingViewHolderType1(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currItem = getItem(position)
        if(holder is UpcomingViewHolderType1){
            holder.bind(currItem)
        }else if(holder is UpcomingViewHolderType2){
            holder.bind(currItem)
        }
    }

    companion object {
        private const val TYPE_1 = 1
        private const val TYPE_2 = 2

        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<ListEventsItem>(){
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