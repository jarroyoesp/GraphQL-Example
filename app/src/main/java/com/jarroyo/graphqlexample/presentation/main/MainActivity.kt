package com.jarroyo.graphqlexample.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jarroyo.graphqlexample.R
import com.jarroyo.graphqlexample.databinding.ActivityMainBinding
import com.jarroyo.graphqlexample.domain.model.Country
import com.jarroyo.graphqlexample.presentation.main.model.UIHomeState
import com.jarroyo.graphqlexample.presentation.main.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
    private lateinit var binding: ActivityMainBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModelObservers()
        viewModel.getHomeData()
    }
    /**
     * Initialize ViewModel Observers
     */
    private fun initViewModelObservers() {
        viewModel.homeState.observe(this, Observer(::handleUI))
    }

    private fun handleUI(state: UIHomeState) {
        Log.d(TAG, "[handleUI] $state")
        when (state) {
            UIHomeState.Error -> showError()
            UIHomeState.NoContents -> { }
            is UIHomeState.ShowData -> showData(state.homeData)
            UIHomeState.HideLoading -> {}
            UIHomeState.ShowLoading -> {}
        }
    }

    private fun showError() {
        Toast.makeText(this, "showError", Toast.LENGTH_SHORT).show()
    }

    private fun showData(homeData: List<Country>) {
        Toast.makeText(this, "$homeData", Toast.LENGTH_SHORT).show()
    }
}