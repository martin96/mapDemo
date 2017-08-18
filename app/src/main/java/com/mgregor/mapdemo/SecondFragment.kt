package com.mgregor.mapdemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_second.view.*

/**
 * Created by mgregor on 8/4/2017.
 */
class SecondFragment : Fragment() {
	private lateinit var recyclerView: RecyclerView

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		Log.e("Second", "onCreateView")

		val rootView = inflater.inflate(R.layout.fragment_second, container, false)
		recyclerView = rootView.recycler

		recyclerView.layoutManager = LinearLayoutManager(context)

		recyclerView.adapter = MyAdapter(items)

		return rootView
	}

	fun addPlace(name: String) {
		items.add(0, name)
	}

	companion object {
		fun newInstance() = SecondFragment()
		var items: MutableList<String> = MutableList(2, { i -> val a = i + 1; (a * a).toString() })
	}
}