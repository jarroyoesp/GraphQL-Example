package com.jarroyo.graphqlexample.presentation.main.model

import com.jarroyo.graphqlexample.domain.model.Country
import java.lang.Exception


sealed class UIHomeState {
    data class ShowData(val homeData: List<Country>) : UIHomeState()
    object NoContents : UIHomeState()
    data class  Error(val exception: Exception) : UIHomeState()
    object ShowLoading : UIHomeState()
    object HideLoading : UIHomeState()
}