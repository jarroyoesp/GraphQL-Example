package com.jarroyo.graphqlexample.presentation.main.model

import com.jarroyo.graphqlexample.domain.model.CharacterUIModel
import java.lang.Exception


sealed class UIHomeState {
    data class ShowData(val homeData: List<CharacterUIModel>) : UIHomeState()
    object NoContents : UIHomeState()
    data class  Error(val exception: Exception) : UIHomeState()
    object ShowLoading : UIHomeState()
    object HideLoading : UIHomeState()
}