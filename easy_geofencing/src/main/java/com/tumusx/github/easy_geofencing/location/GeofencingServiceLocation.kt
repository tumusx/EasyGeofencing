package com.tumusx.github.easy_geofencing.location

import android.content.Context
import android.location.Location
import android.location.LocationRequest
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.concurrent.TimeUnit

class UpdateLocation(context: Context) {
    private lateinit var locationCallback: LocationCallback
    private val fusedLocation: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    companion object {
        const val GREAT_THAN_NUMBER = 1

        //todo change for data storage
        var goodLastLocation: Location? = null
    }

    private fun onCurrentLocation() : LocationRequest {
    }

    fun requestNewLocation() {
        fusedLocation.requestLocationUpdates()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(location: LocationResult) {
                for (newLocation in location.locations) {
                    if (newLocation.accuracy.compareTo(100) <= GREAT_THAN_NUMBER) {
                        goodLastLocation = newLocation
                        Log.d("New Location", newLocation.toString())
                    }
                }
                super.onLocationResult(location)
            }
        }
    }

    fun stopLocationRequest() {
        locationCallback.
    }
}