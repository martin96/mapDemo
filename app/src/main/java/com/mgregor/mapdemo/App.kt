package com.mgregor.mapdemo

import android.app.Application
import com.mgregor.mapdemo.di.DaggerFirebaseComponent
import com.mgregor.mapdemo.di.FirebaseComponent

/**
 * Created by mgregor on 8/21/2017.
 */
class App : Application() {

	companion object {
		lateinit var firebaseComponent: FirebaseComponent
	}

	override fun onCreate() {
		super.onCreate()
		firebaseComponent = DaggerFirebaseComponent.builder()
				.build()
	}
}