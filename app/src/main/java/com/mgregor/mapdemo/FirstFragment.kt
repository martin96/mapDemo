package com.mgregor.mapdemo

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_first.*
import javax.inject.Inject

/**
 * Created by mgregor on 8/4/2017.
 */
class FirstFragment : Fragment(), GoogleApiClient.OnConnectionFailedListener {

	override fun onConnectionFailed(p0: ConnectionResult) {
	}

	/*private lateinit var googleApiClient: GoogleApiClient
	private lateinit var mMap: GoogleMap
	private var mLocationPermissionGranted: Boolean = false
	private var mLastKnownLocation: Location? = null
	private var mCameraPosition: CameraPosition? = null
	private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: Int = 100*/

	@Inject lateinit var databaseReference: DatabaseReference
	private lateinit var googleApiClient: GoogleApiClient
	private lateinit var auth: FirebaseAuth

	companion object {
		fun newInstance() = FirstFragment()
		val PLACE_PICKER_REQUEST: Int = 1
		val PLACE_PICKER_REQUEST_HOME: Int = 2
		val PLACE_PICKER_REQUEST_WORK: Int = 3
		val SIGN_IN_REQUEST: Int = 10
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		App.firebaseComponent.inject(this)

		val googleSignInOptions: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestIdToken("966990732949-dbpdnjesd6tgrpo7i7vmlkmu7rmhuu1b.apps.googleusercontent.com")
				.requestEmail()
				.build()

		googleApiClient = GoogleApiClient.Builder(context)
				.enableAutoManage(activity, this)
				.addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
				.build()

		auth = FirebaseAuth.getInstance()
	}

	override fun onStart() {
		super.onStart()
		val currentUser = auth.currentUser
		user.text = "${currentUser?.displayName}\n${currentUser?.email}"
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_first, container, false)

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		cardText.setOnClickListener {
			startActivityForResult(PlacePicker.IntentBuilder().build(activity), PLACE_PICKER_REQUEST)
		}

		homeLayout.setOnClickListener {
			startActivityForResult(PlacePicker.IntentBuilder().build(activity), PLACE_PICKER_REQUEST_HOME)
		}

		workLayout.setOnClickListener {
			startActivityForResult(PlacePicker.IntentBuilder().build(activity), PLACE_PICKER_REQUEST_WORK)
		}

		signInButton.setOnClickListener {
			startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(googleApiClient), SIGN_IN_REQUEST)
		}

		databaseReference.child("1").child("home").addValueEventListener(object : ValueEventListener {
			override fun onCancelled(p0: DatabaseError?) {
			}

			override fun onDataChange(data: DataSnapshot?) {
				homeText.text = data?.value.toString()
			}
		})

		databaseReference.child("1").child("work").addValueEventListener(object : ValueEventListener {
			override fun onCancelled(p0: DatabaseError?) {
			}

			override fun onDataChange(data: DataSnapshot?) {
				workText.text = data?.value.toString()
			}
		})

		button.setOnClickListener {
			val cx = card.width / 2

			val finalRadius = Math.hypot(cx.toDouble(), 650.0)

			val anim = ViewAnimationUtils.createCircularReveal(new_card, cx, -50, 0f, finalRadius.toFloat())

			val translate = ValueAnimator.ofFloat(0.0f, 600.0f)
			translate.addUpdateListener { animation ->
				val value: Float = animation.animatedValue as Float
				new_card.layoutParams.height = value.toInt()
				new_card.requestLayout()
			}

			val transition = AnimatorSet()
			transition.playTogether(anim, translate)
			transition.interpolator = FastOutSlowInInterpolator()
			transition.duration = 800

			transition.start()
			new_card.visibility = View.VISIBLE
		}

		/*googleApiClient = GoogleApiClient.Builder(context)
				.enableAutoManage(activity, this)
				.addConnectionCallbacks(this)
				.addApi(LocationServices.API)
				.addApi(Places.GEO_DATA_API)
				.addApi(Places.PLACE_DETECTION_API)
				.build()
		googleApiClient.connect()*/
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		Log.e("FirstFragment", "Look, it's here!!")
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == SIGN_IN_REQUEST) {
				val result: GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

				if (result.isSuccess) {
					val account: GoogleSignInAccount? = result.signInAccount
					firebaseAuthWithGoogle(account)
					//user.text = "${account?.displayName}\n${account?.email}"
				}
			} else {
				val place: Place? = PlacePicker.getPlace(context, data)

				val currentUser: FirebaseUser? = auth.currentUser

				when (requestCode) {
					PLACE_PICKER_REQUEST_HOME -> {
						databaseReference.child(currentUser?.uid ?: "1").child("home").setValue(place?.name)
					}
					PLACE_PICKER_REQUEST_WORK -> {
						databaseReference.child(currentUser?.uid ?: "1").child("work").setValue(place?.name)
					}
				}
			}
		}
	}

	private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
		val credential: AuthCredential = GoogleAuthProvider.getCredential(account?.idToken, null)
		auth.signInWithCredential(credential)
				.addOnCompleteListener { task ->
					if (task.isSuccessful) {
						//val firebaseUser: FirebaseUser? = auth.currentUser
						//user.text = "${firebaseUser?.displayName}\n${firebaseUser?.email}"
					}
				}
	}

	/*override fun onConnected(p0: Bundle?) {
		val mapFragment = childFragmentManager
				.findFragmentById(R.id.map) as SupportMapFragment
		mapFragment.getMapAsync(this)
	}

	override fun onConnectionSuspended(p0: Int) {
	}

	override fun onConnectionFailed(p0: ConnectionResult) {
	}

	override fun onMapReady(googleMap: GoogleMap) {
		mMap = googleMap
		mMap.setOnPoiClickListener(this)

		updateLocationUI()
		getDeviceLocation()
	}

	override fun onPoiClick(poi: PointOfInterest) {
		mMap.moveCamera(CameraUpdateFactory.newLatLng(poi.latLng))
	}

	fun updateLocationUI() {
		if (ContextCompat.checkSelfPermission(activity.applicationContext,
				android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			mLocationPermissionGranted = true
		} else {
			ActivityCompat.requestPermissions(activity,
					arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
					PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
		}

		if (mLocationPermissionGranted) {
			mMap.isMyLocationEnabled = true
			mMap.uiSettings.isMyLocationButtonEnabled = false
		} else {
			mMap.isMyLocationEnabled = false
			mMap.uiSettings.isMyLocationButtonEnabled = false
			mLastKnownLocation = null
		}
	}

	private fun getDeviceLocation() {
		if (ContextCompat.checkSelfPermission(activity.applicationContext,
				android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			mLocationPermissionGranted = true
		} else {
			ActivityCompat.requestPermissions(activity,
					arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
					PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
		}

		if (mLocationPermissionGranted) {
			mLastKnownLocation = LocationServices.FusedLocationApi
					.getLastLocation(googleApiClient)
		}

		// Set the map's camera position to the current location of the device.
		if (mCameraPosition != null) {
			mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition))
		} else if (mLastKnownLocation != null) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
					LatLng(mLastKnownLocation!!.latitude,
							mLastKnownLocation!!.longitude), 15.0f))
		} else {
			mMap.uiSettings.isMyLocationButtonEnabled = false
		}
	}

	override fun onRequestPermissionsResult(requestCode: Int,
											permissions: Array<String>,
											grantResults: IntArray) {
		mLocationPermissionGranted = false
		when (requestCode) {
			PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
				if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					mLocationPermissionGranted = true
				}
			}
		}
		updateLocationUI()
	}*/
}