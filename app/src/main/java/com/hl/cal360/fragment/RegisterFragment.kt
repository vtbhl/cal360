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
import com.hl.cal360.network.WebService




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


        var path = "https://jsonplaceholder.typicode.com/posts"
        var textInput = "Đội Cấn, Hà Nội"
        var path2 = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + textInput + "&inputtype=textquery&fields=photos,formatted_address,name,rating,opening_hours,geometry&key=" + getString(R.string.MAP_API_KEY)
        WebService(path2).execute()

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Add a marker in Sydney, Australia, and move the camera.
        val sydney = LatLng(-34.0, 151.0)
        googleMap.uiSettings.setAllGesturesEnabled(true)
       // var cameraPosition: CameraPosition =  CameraPosition.Builder().target(sydney).zoom(15.0f).build()
        //var cameraUpdate: CameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
       //googleMap.moveCamera(cameraUpdate)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
