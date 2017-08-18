package com.mgregor.mapdemo

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by mgregor on 7/31/2017.
 */

class MyAdapter(private val mDataset: MutableList<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

	class ViewHolder(cardView: CardView) : RecyclerView.ViewHolder(cardView) {
		val mTextView: TextView = cardView.findViewById(R.id.textView)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false) as CardView
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		if(position % 2 == 0) {
			holder.mTextView.text = "Alza " + mDataset[position]
		} else {
			holder.mTextView.text = "Sportisimo " + mDataset[position]
		}
	}

	override fun getItemCount(): Int {
		return mDataset.size
	}
}