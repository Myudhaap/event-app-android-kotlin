package dev.mayutama.project.eventapp.ui.notification

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.mayutama.project.eventapp.base.BaseActivity
import dev.mayutama.project.eventapp.databinding.ActivityNotificationBinding
import dev.mayutama.project.eventapp.util.Result
import dev.mayutama.project.eventapp.util.Util

class NotificationActivity :
    BaseActivity<ActivityNotificationBinding>(ActivityNotificationBinding::inflate)
{
    private lateinit var adapter: NotificationAdapter
    private val notificationViewModel: NotificationViewModel by viewModels {
        NotificationViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initProperties()
        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        observeNotificationList()
    }

    private fun initProperties(){
        adapter = NotificationAdapter()
    }

    private fun observeNotificationList(){
        val rvNotification = binding.rvNotification
        rvNotification.layoutManager = LinearLayoutManager(this@NotificationActivity, LinearLayoutManager.VERTICAL, false)
        rvNotification.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvNotification.adapter = adapter

        notificationViewModel.getNotificationList().observe(this){
            when(it){
                is Result.Loading -> {
                    showLoading()
                }
                is Result.Success -> {
                    hideLoading()

                    adapter.submitList(it.data)
                }
                is Result.Error -> {
                    hideLoading()
                    Util.showToast(this, it.error)
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
}