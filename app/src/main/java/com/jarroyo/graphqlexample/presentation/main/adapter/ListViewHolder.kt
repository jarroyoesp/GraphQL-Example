package com.jarroyo.graphqlexample.presentation.main.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jarroyo.graphqlexample.R
import com.jarroyo.graphqlexample.domain.model.CharacterUIModel
import com.squareup.picasso.Picasso

class ListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        private val TAG = ListViewHolder.javaClass.simpleName
    }

    private val textViewTitle: TextView = view.findViewById(R.id.item_rv_textview_title)
    private val textViewId: TextView = view.findViewById(R.id.item_rv_textview_id)
    private val textViewPrice: TextView = view.findViewById(R.id.item_rv_textview_price)
    private val imageView: ImageView = view.findViewById(R.id.item_rv_imageView)
    private var layoutMain: ViewGroup = view.findViewById(R.id.item_rv_layout_main)

    fun bind(position: Int, item: CharacterUIModel, listener: (CharacterUIModel) -> Unit) {
        Log.d(TAG, "[bind]")
        textViewTitle.text = "${item.name}"
        textViewId.text = "${item.id}"
        Picasso.with(view.context).load(item.image).into(imageView)
        layoutMain.setOnClickListener {
            listener(item)
        }
    }
}