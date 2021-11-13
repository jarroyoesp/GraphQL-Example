package com.jarroyo.graphqlexample.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jarroyo.graphqlexample.R
import com.jarroyo.graphqlexample.domain.model.CharacterUIModel
import com.jarroyo.graphqlexample.presentation.utils.notifyChanges
import kotlin.properties.Delegates

class ListRVAdapter(
    private var onClickItemListener: (CharacterUIModel) -> Unit
) : RecyclerView.Adapter<ListViewHolder>() {

    var dataSet: List<CharacterUIModel> by Delegates.observable(emptyList()) { _, oldList, newList ->
        notifyChanges(oldList, newList) { o, n -> o.id == n.id }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_rv, viewGroup, false)

        return ListViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
        viewHolder.bind(position, dataSet[position], onClickItemListener)
    }

    override fun getItemCount() = dataSet.size

    fun updateList(list: List<CharacterUIModel>) {
        dataSet = list
    }

}