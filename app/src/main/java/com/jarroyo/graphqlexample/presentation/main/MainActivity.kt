package com.jarroyo.graphqlexample.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jarroyo.graphqlexample.R
import com.jarroyo.graphqlexample.databinding.ActivityMainBinding
import com.jarroyo.graphqlexample.domain.model.CharacterUIModel
import com.jarroyo.graphqlexample.presentation.main.adapter.ListRVAdapter
import com.jarroyo.graphqlexample.presentation.main.model.UIHomeState
import com.jarroyo.graphqlexample.presentation.main.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: ListRVAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initViewModelObservers()
        getData()
    }
    /**
     * Initialize ViewModel Observers
     */
    private fun initViewModelObservers() {
        viewModel.homeState.observe(this, Observer(::handleUI))
    }

    private fun initRecyclerView() {
        binding.activityMainSwipeRefreshlayout.setOnRefreshListener {
            getData()
        }

        val layoutManager = LinearLayoutManager(this)
        binding.activityMainRv.layoutManager = layoutManager

        adapter = ListRVAdapter(onClickItemListener = {

        })
        binding.activityMainRv.adapter = adapter

        // Separator
        var itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_rv_transparent)!!)
        binding.activityMainRv.addItemDecoration(itemDecoration)
    }

    private fun getData() {
        viewModel.getHomeData()
    }

    private fun handleUI(state: UIHomeState) {
        Log.d(TAG, "[handleUI] $state")
        when (state) {
            is UIHomeState.Error -> showError(state.exception)
            UIHomeState.NoContents -> { }
            is UIHomeState.ShowData -> showData(state.homeData)
            UIHomeState.HideLoading -> hideLoading()
            UIHomeState.ShowLoading -> showLoading()
        }
    }
    private fun showLoading() {
        Log.d(TAG, "[showLoading]")
        binding.activityMainSwipeRefreshlayout.isRefreshing = true
        binding.activityMainLayoutError.visibility = View.GONE
    }

    private fun hideLoading() {
        Log.d(TAG, "[hideLoading]")
        binding.activityMainSwipeRefreshlayout.isRefreshing = false
    }

    private fun showError(e: Exception) {
        Log.d(TAG, "[showError]")
        binding.activityMainLayoutError.visibility = View.VISIBLE
        binding.activityMainLayoutErrorTv.text = e.localizedMessage
        binding.activityMainRv.visibility = View.GONE
    }

    private fun showData(homeData: List<CharacterUIModel>) {
        Log.d(TAG, "[showData]")
        adapter.updateList(homeData)
    }
}