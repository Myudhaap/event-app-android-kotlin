package dev.mayutama.project.eventapp.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.viewModels
import com.google.android.material.search.SearchView
import dev.mayutama.project.eventapp.R
import dev.mayutama.project.eventapp.base.BaseFragment
import dev.mayutama.project.eventapp.databinding.FragmentHomeBinding
import dev.mayutama.project.eventapp.ui.main.MainActivity

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    OnClickListener
{
    private lateinit var searchView: SearchView

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProperties(view)
        init()
        initListener()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.search_bar -> onClickSearchBar()
        }
    }

    private fun init() {
    }

    private fun initProperties(view: View) {
    }

    private fun initListener() {
        binding.searchBar.setOnClickListener(this)
    }

    private fun onClickSearchBar() {
        (requireActivity() as MainActivity).onClickSearch()
    }
}