package com.mgregor.mapdemo.di

import com.mgregor.mapdemo.FirstFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by mgregor on 8/21/2017.
 */
@Singleton
@Component(modules = arrayOf(
		FirebaseModule::class
))
interface FirebaseComponent {
	fun inject(firstFragment: FirstFragment)
}