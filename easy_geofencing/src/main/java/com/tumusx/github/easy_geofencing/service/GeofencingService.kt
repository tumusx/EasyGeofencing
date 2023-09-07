package com.tumusx.github.easy_geofencing.service

import android.content.Context
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

/**
 * @author Murillo Alves
 * @see <a href="https://developer.android.com/training/location/geofencing">Android Documentation</a>
 */
class GeofencingService {
    private var applicationContext: Context? = null
    private var initialTrigger: Int? = null
    private var geofencingsList: List<Geofence>? = null
    private var geofenceRequest: GeofencingRequest? = null


    fun onConfigGeofencing(geofencingList: List<Geofence>, context: Context): GeofencingService {
        geofencingsList = geofencingList
        applicationContext = context
        val geofencingClient = LocationServices.getGeofencingClient(context)
        return this
    }

    fun onGeofencingRequest(): GeofencingService {
        val geofencingRequest = with(GeofencingRequest.Builder()) {
            setInitialTrigger(initialTrigger ?: 0)
            addGeofences(geofencingsList ?: listOf<Geofence>())
        }
        geofenceRequest = geofencingRequest.build()
        return this
    }

    fun onConfigureTriggerGeofencing(triggerInitial: Int): GeofencingService {
        initialTrigger = triggerInitial
        return this
    }

    fun builder(): GeofencingService {
        return GeofencingService()
    }
}