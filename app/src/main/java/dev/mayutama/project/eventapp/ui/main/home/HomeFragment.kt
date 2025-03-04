package dev.mayutama.project.eventapp.ui.main.home

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dev.mayutama.project.eventapp.R
import dev.mayutama.project.eventapp.base.BaseFragment
import dev.mayutama.project.eventapp.databinding.FragmentHomeBinding
import dev.mayutama.project.eventapp.ui.main.MainActivity
import dev.mayutama.project.eventapp.util.Result
import dev.mayutama.project.eventapp.util.Util
import dev.mayutama.project.eventapp.util.hide
import dev.mayutama.project.eventapp.util.show

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    OnClickListener
{
    private lateinit var context: MainActivity

    private val homeViewModel: HomeViewModel by viewModels{
        HomeViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProperties()
        init()
        initListener()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.search_bar -> onClickSearchBar()
        }
    }

    private fun init() {
        getUpcomingEvent()
        getFinishedEvent()
    }

    private fun getUpcomingEvent() {
        val viewPager2 = binding.vpUpcoming

        homeViewModel.listEventUpcoming.observe(viewLifecycleOwner){
            when(it) {
                is Result.Loading -> {
                    context.showLoading()
                }
                is Result.Success -> {
                    context.hideLoading()
                    val adapter = UpcomingBannerAdapter()
                    adapter.submitList(it.data)
                    viewPager2.adapter = adapter
                    binding.dotsUpcoming.attachTo(viewPager2)

                    if(adapter.currentList.size == 0){
                        viewPager2.hide()
                        binding.dotsUpcoming.hide()
                    }else{
                        viewPager2.show()
                        binding.dotsUpcoming.show()
                    }
                }
                is Result.Error -> {
                    context.hideLoading()
                    Util.showSnackBar(binding.root, it.error)
                }
            }
        }
    }

    private fun getFinishedEvent() {
        homeViewModel.listEventFinished.observe(viewLifecycleOwner){
            when(it){
                is Result.Loading -> {
                    context.showLoading()
                }
                is Result.Success -> {
                    context.hideLoading()

                    val adapter = FinishedEventAdapter()
                    adapter.submitList(it.data)

                    val layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
                    binding.rvFinished.apply {
                        this.adapter = adapter
                        this.layoutManager = layoutManager
                    }

                    if(adapter.currentList.size == 0){
                        binding.rvFinished.hide()
                    }else{
                        binding.rvFinished.show()
                    }
                }
                is Result.Error -> {
                    context.hideLoading()
                    Util.showSnackBar(binding.root, it.error)
                }
            }
        }
    }

    private fun initProperties() {
        context = requireActivity() as MainActivity
    }

    private fun initListener() {
        binding.searchBar.setOnClickListener(this)
    }

    private fun onClickSearchBar() {
        context.onClickSearch()
    }
}