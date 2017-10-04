package com.mgregor.mapdemo.common

import android.view.View
import android.view.ViewTreeObserver

/**
 * Created by mgregor on 10/4/2017.
 */
inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
	viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
		override fun onGlobalLayout() {
			if (measuredWidth > 0 && measuredHeight > 0) {
				viewTreeObserver.removeOnGlobalLayoutListener(this)
				f()
			}
		}
	})
}