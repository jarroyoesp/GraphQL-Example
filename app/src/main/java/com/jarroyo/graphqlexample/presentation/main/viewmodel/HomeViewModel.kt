package com.jarroyo.graphqlexample.presentation.main.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarroyo.graphqlexample.domain.usecase.GetDataHomeUsecase
import com.jarroyo.graphqlexample.presentation.main.model.UIHomeState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class HomeViewModel
@ViewModelInject
constructor(
    private val getDataHomeUsecase: GetDataHomeUsecase
) : ViewModel() {

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }

    private var _homeState = MutableLiveData<UIHomeState>()
    val homeState: LiveData<UIHomeState>
        get() {
            if (_homeState.value == null) getHomeData()
            return _homeState
        }


    fun getHomeData() = viewModelScope.launch {
        _homeState.value = UIHomeState.ShowLoading

        getDataHomeUsecase.invoke().fold(
            {
                _homeState.value = UIHomeState.HideLoading
                _homeState.value = UIHomeState.Error
            },
            { listPoiItem ->
                _homeState.value = UIHomeState.HideLoading
                if (listPoiItem.isNullOrEmpty()) {
                    _homeState.value = UIHomeState.NoContents
                } else {
                    _homeState.value = UIHomeState.ShowData(listPoiItem)
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}