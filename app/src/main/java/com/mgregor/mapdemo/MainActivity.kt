package com.mgregor.mapdemo

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
	private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		setSupportActionBar(toolbar)

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

		container.setPageTransformer(true, DepthPageTransformer())
		// Set up the ViewPager with the sections adapter.
		container.adapter = mSectionsPagerAdapter

		//text = findViewById(R.id.text)
	}

	inner class DepthPageTransformer : ViewPager.PageTransformer {

		override fun transformPage(view: View, position: Float) {
			val pageWidth = view.width

			if (position <= 1) {
				val v = view.findViewById<ConstraintLayout>(R.id.firstLayout)
				if (v != null) {
					v.translationX = (-position * (pageWidth * 0.8f))
				}
			}
		}
	}

	inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

		override fun getItem(position: Int): Fragment {
			return when (position) {
				0 -> FirstFragment.newInstance()
				1 -> SecondFragment.newInstance()
				else -> FirstFragment.newInstance()
			}
		}

		override fun getCount(): Int {
			return 2
		}

		override fun getPageWidth(position: Int): Float {
			return when (position) {
				0 -> 0.95f
				else -> 1f
			}
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
	}

	/*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		Log.e("MainActivity", "its here")
		if (resultCode == RESULT_OK) {
			Log.e("MainActivity", "resultCode OK")
			val place = PlacePicker.getPlace(this, data)
			val fragment = mSectionsPagerAdapter?.getItem(1)
			if (fragment is SecondFragment) {
				fragment.addPlace(place.name.toString())
			}
		}
	}*/
}