package dev.mayutama.project.eventapp.ui.main.upcomming

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dev.mayutama.project.eventapp.base.BaseFragment
import dev.mayutama.project.eventapp.databinding.FragmentUpcommingBinding
import dev.mayutama.project.eventapp.ui.main.EventStaggeredAdapter
import dev.mayutama.project.eventapp.ui.main.MainActivity
import dev.mayutama.project.eventapp.util.Result
import dev.mayutama.project.eventapp.util.Util

class UpcomingFragment :
    BaseFragment<FragmentUpcommingBinding>(FragmentUpcommingBinding::inflate)
{
    private val upcomingViewModel: UpcomingViewModel by viewModels {
        UpcomingViewModelFactory.getInstance(requireActivity().application)
    }

    private lateinit var context: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProperties()
        init()
    }

    private fun init(){
        getUpcomingEvent()
    }

    private fun initProperties(){
        context = requireActivity() as MainActivity
    }

    private fun getUpcomingEvent(){
        upcomingViewModel.listUpcomingEvent.observe(viewLifecycleOwner){
            when(it){
                is Result.Loading -> {
                    context.showLoading()
                }
                is Result.Success -> {
                    context.hideLoading()

                    val adapter = EventStaggeredAdapter()
                    adapter.submitList(it.data)

                    val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    binding.rvUpcoming.apply {
                        this.adapter = adapter
                        this.layoutManager = layoutManager
                    }
                }
                is Result.Error -> {
                    context.hideLoading()
                    Util.showSnackBar(binding.root, it.error)
                }
            }
        }
    }

}