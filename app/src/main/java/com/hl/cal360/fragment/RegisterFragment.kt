package com.hl.cal360.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hl.cal360.R





/**
 * Fragment representing the login screen for Shrine.
 */
class RegisterFragment : Fragment(),OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    lateinit var mMapView :MapView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.cal360_register_fragment, container, false)
        mMapView = view.findViewById(R.id.mapView)
        mMapView.onCreate(savedInstanceState)
        mMapView.onResume()

        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mMapView.getMapAsync (this)
//            OnMapReadyCallback() {
//                fun onMapReady(googleMap: GoogleMap) {
//                    if (googleMap != null) {
//                        var marketLatLng = LatLng(17.385044, 78.486671)
//                        googleMap.uiSettings.setAllGesturesEnabled(true)
//
//                        var cameraPosition: CameraPosition =
//                            CameraPosition.Builder().target(marketLatLng).zoom(15.0f).build()
//                        var cameraUpdate: CameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
//                        googleMap.moveCamera(cameraUpdate)
//                    }
//
//                }
//            }
//        }

        //val mapFragment = activity!!.supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        //mapFragment.getMapAsync(this)

        return view
        // Snippet from "Navigate to the next Fragment" section goes here
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Add a marker in Sydney, Australia, and move the camera.
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
