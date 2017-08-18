package com.mgregor.mapdemo

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * Created by mgregor on 8/4/2017.
 */
class FirstFragment : Fragment() {
	/*private lateinit var googleApiClient: GoogleApiClient
	private lateinit var mMap: GoogleMap
	private var mLocationPermissionGranted: Boolean = false
	private var mLastKnownLocation: Location? = null
	private var mCameraPosition: CameraPosition? = null
	private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: Int = 100*/

	companion object {
		fun newInstance() = FirstFragment()
		val PLACE_PICKER_REQUEST: Int = 1
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_first, container, false)

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		cardText.setOnClickListener {
			startActivityForResult(PlacePicker.IntentBuilder().build(activity), PLACE_PICKER_REQUEST)
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