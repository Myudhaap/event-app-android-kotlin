package dev.mayutama.project.eventapp.ui.eventDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import dev.mayutama.project.eventapp.R
import dev.mayutama.project.eventapp.base.BaseActivity
import dev.mayutama.project.eventapp.data.remote.response.ListEventsItem
import dev.mayutama.project.eventapp.databinding.ActivityEventDetailBinding
import dev.mayutama.project.eventapp.util.Result
import dev.mayutama.project.eventapp.util.Util
import okhttp3.internal.format

class EventDetailActivity :
    BaseActivity<ActivityEventDetailBinding>(ActivityEventDetailBinding::inflate),
        OnClickListener
{
    private var isFavorite: Boolean? = null
    private var event: ListEventsItem? = null

    private val eventDetailViewModel: EventDetailViewModel by viewModels {
        EventDetailViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init(savedInstanceState)
        initListener()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_favorite -> {
                isFavorite?.let {
                    Log.d(TAG, "onClick:  isFavorite: $isFavorite || event: $event")
                    if(!it){
                        eventDetailViewModel.setFavorite(event!!)
                    }else{
                        eventDetailViewModel.unsetFavorite(event!!)
                    }
                }
            }
            R.id.btn_back -> finish()
            R.id.btn_register -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event?.link))
                startActivity(intent)
            }
        }
    }

    private fun init(savedInstanceState: Bundle?){
        val id = intent.getIntExtra(EXTRA_ID, 0)
        observeEventDetail()
        observeIsFavorite()

        if(savedInstanceState == null){
            eventDetailViewModel.getEventById(id)
            eventDetailViewModel.getIsEventFavorite(id)
        }
    }

    private fun initListener(){
        binding.btnFavorite.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun observeEventDetail(){
        eventDetailViewModel.event.observe(this){ state ->
            when(state){
                is Result.Loading -> {
                    showLoading()
                }
                is Result.Success -> {
                    hideLoading()

                    event = state.data


                    event?.let{
                        Glide.with(this)
                            .load(it.mediaCover)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.placeholder_image)
                            .into(binding.imgEvent)

                        binding.tvTitle.text = it.name
                        if(it.cityName?.lowercase() != "online"){
                            binding.tvCityName.background = resources.getDrawable(R.drawable.bg_offline)
                        }
                        binding.tvCityName.text = it.cityName
                        binding.tvCategory.text = it.category
                        binding.tvQuota.text = format(getString(R.string.quoat_format, it.quota))
                        binding.tvTimes.text = format(getString(R.string.datetimes_format, "${it.beginTime} s\\d ${it.endTime}"))
                        binding.tvDescription.text = Html.fromHtml(it.description, Html.FROM_HTML_MODE_COMPACT)
                    }
                }
                is Result.Error -> {
                    hideLoading()

                    Util.showToast(this, state.error)
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun observeIsFavorite(){
        eventDetailViewModel.isFavorite.observe(this){ state ->
            when(state){
                is Result.Loading -> {
                    showLoading()
                }

                is Result.Success -> {
                    hideLoading()

                    isFavorite = state.data

                    if(state.data){
                        binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
                    }else{
                        binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
                    }
                }
                is Result.Error -> {
                    hideLoading()

                    Util.showToast(this, state.error)
                }
            }
        }
    }

    private fun showLoading(){
        binding.loadingLayout.root.visibility = View.VISIBLE
        Util.disableScreenAction(window)
    }

    private fun hideLoading(){
        binding.loadingLayout.root.visibility = View.GONE
        Util.enableScreenAction(window)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        val TAG: String = EventDetailActivity::class.java.simpleName
    }
}