package com.jarroyo.graphqlexample.presentation.main.model

import com.jarroyo.graphqlexample.domain.model.Country


sealed class UIHomeState {
    data class ShowData(val homeData: List<Country>) : UIHomeState()
    object NoContents : UIHomeState()
    object Error : UIHomeState()
    object ShowLoading : UIHomeState()
    object HideLoading : UIHomeState()
}