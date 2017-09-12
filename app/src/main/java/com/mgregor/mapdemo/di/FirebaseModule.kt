package com.mgregor.mapdemo.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by mgregor on 8/21/2017.
 */
@Module
class FirebaseModule {

	@Provides
	@Singleton
	fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

	@Provides
	@Singleton
	fun provideDatabaseReference(firebaseDatabase: FirebaseDatabase): DatabaseReference = firebaseDatabase.getReference("users")
}