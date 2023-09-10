package com.tumusx.github.easy_geofencing.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class UpdateLocation(
    context: Context,
    private var timeRequestLocation: Long,
    private var minUpdateDistance: Float
) : LocationCallback() {
    private val fusedLocation: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    companion object {
        const val GREAT_THAN_NUMBER = 1

        //todo change for data storage
        var goodLastLocation: Location? = null
    }

    init {
        requestNewLocation()
    }

    fun updateMinDistance(newMinUpdateLocation: Float) = newMinUpdateLocation.also { minDistance ->
        minUpdateDistance = minDistance
        requestNewLocation()
    }

    private fun onLocationRequest(): LocationRequest {
        return LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, timeRequestLocation).apply {
            setMinUpdateDistanceMeters(minUpdateDistance)
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()
    }

    override fun onLocationResult(location: LocationResult) {
        for (newLocation in location.locations) {
            if (newLocation.accuracy.compareTo(100) <= GREAT_THAN_NUMBER) {
                goodLastLocation = newLocation
                Log.d("New Location", newLocation.toString())
            }
        }
        super.onLocationResult(location)
    }

    fun changeTimeRequestLocation(newTimeRequest: Long) = newTimeRequest.also { timeRequest ->
        timeRequestLocation = timeRequest
        requestNewLocation()
    }


    @SuppressLint("MissingPermission")
    fun requestNewLocation() {
        fusedLocation.requestLocationUpdates(
            onLocationRequest(),
            this@UpdateLocation,
            Looper.getMainLooper()
        )
    }

    override fun onLocationAvailability(locationAvailability: LocationAvailability) {
        Log.d("isLocationAvailability: ", locationAvailability.isLocationAvailable.toString())
        super.onLocationAvailability(locationAvailability)
    }
}