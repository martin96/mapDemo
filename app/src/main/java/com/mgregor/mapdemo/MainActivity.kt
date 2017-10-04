package com.mgregor.mapdemo

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
	private lateinit var mSectionsPagerAdapter: SectionsPagerAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		setSupportActionBar(toolbar)

		mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

		container.setPageTransformer(true) { page, position ->
			val pageWidth = page.width

			when {
				position < -1 -> page.alpha = 0F
				position <= 1 -> if (position < 0) {
					page.translationX = (-position * (pageWidth * 0.8F))
				}
				else -> page.alpha = 0F
			}
		}

		container.adapter = mSectionsPagerAdapter
	}

	inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

		override fun getItem(position: Int) = when (position) {
			0 -> FirstFragment.newInstance()
			1 -> SecondFragment.newInstance()
			else -> FirstFragment.newInstance()
		}

		override fun getCount() = 2

		override fun getPageWidth(position: Int) = when (position) {
			0 -> 0.95f
			else -> 1f
		}
	}
}