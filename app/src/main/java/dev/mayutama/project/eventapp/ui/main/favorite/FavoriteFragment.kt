package dev.mayutama.project.eventapp.ui.main.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dev.mayutama.project.eventapp.base.BaseFragment
import dev.mayutama.project.eventapp.databinding.FragmentFavoriteBinding
import dev.mayutama.project.eventapp.ui.main.MainActivity
import dev.mayutama.project.eventapp.util.Result
import dev.mayutama.project.eventapp.util.Util

class FavoriteFragment :
    BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate)
{
    private val favoriteViewModel: FavoriteViewModel by viewModels { FavoriteViewModelFactory
        .getInstance(requireActivity().application)
    }

    private lateinit var context: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProperties()

        if(savedInstanceState == null){
            getFavoriteEvent()
        }

        init()
    }

    private fun initProperties(){
        context = requireActivity() as MainActivity
    }

    private fun init(){

    }

    private fun getFavoriteEvent(){
        val rvFavorite = binding.rvFavorite
        favoriteViewModel.getEventFavoriteList().observe(viewLifecycleOwner, {
            when(it) {
                is Result.Loading -> {
                    context.showLoading()
                }
                is Result.Success -> {
                    context.hideLoading()

                    val adapter = FavoriteAdapter()
                    adapter.submitList(it.data)
                    val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    rvFavorite.adapter = adapter
                    rvFavorite.layoutManager = layoutManager
                }
                is Result.Error -> {
                    context.hideLoading()

                    Util.showToast(context, it.error)
                }
            }
        })
    }
}