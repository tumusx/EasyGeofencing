package com.tumusx.github.easy_geofencing.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT
import com.google.android.gms.location.Geofence.NEVER_EXPIRE
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.tumusx.github.easy_geofencing.location.UpdateLocation

/**
 * @author Murillo Alves
 * @see <a href="https://developer.android.com/training/location/geofencing">Android Documentation</a>
 */
class GeofencingService private constructor(
    context: Context?,
    initialTriggerTimer: Int,
    radiusGeofencing: Float,
    geofenceList: MutableMap<String, Geofence>,
    pendingIntent: PendingIntent?
) {
    class Builder {
        private var context: Context? = null
        private var initialTriggerTimer: Int = 0
        private var locationGeofencing: Location? = UpdateLocation.goodLastLocation
        private var radiusGeofencing = 0F
        private var geofenceList: MutableMap<String, Geofence> = mutableMapOf()
        private var pendingIntent: PendingIntent? = null


        fun setPendingIntent(newPendingIntent: PendingIntent): Builder {
            pendingIntent = newPendingIntent
            return this
        }

        fun setRadius(radius: Float): Builder {
            this.radiusGeofencing = radius
            return this
        }


        fun setInitialTrigger(triggerTime: Int): Builder {
            this.initialTriggerTimer = triggerTime
            return this
        }

        fun addGeofence(
            key: String,
            location: Location,
            radiusInMeters: Float = 100.0f,
            expirationTimeInMillis: Long = 30 * 60 * 1000,
        ) : Builder {
            geofenceList[key] = createGeofence(key, location, radiusInMeters, expirationTimeInMillis)
            return this
        }

        @SuppressLint("MissingPermission")
        fun addGeofencingClient(context: Context): Builder {
            this.context = context
            val geofencingServiceLocation = LocationServices.getGeofencingClient(context)
            pendingIntent?.let { geofencingServiceLocation.addGeofences(createGeofencing(), it) }
            return this
        }

        private fun createGeofencing(): GeofencingRequest {
             val a = GeofencingRequest.Builder().setInitialTrigger(initialTriggerTimer)
                .addGeofences(
                    geofenceList.values.toList()
                )
                .build()
            Log.d("Geofences", a.geofences.toString())
            return a
        }

        private fun createGeofence(
            key: String,
            location: Location,
            radiusInMeters: Float,
            expirationTimeInMillis: Long): Geofence {
            return Geofence.Builder()
                .setRequestId(key)
                .setCircularRegion(location.latitude, location.longitude, 2f)
                .setExpirationDuration(expirationTimeInMillis)
                .setTransitionTypes(GEOFENCE_TRANSITION_ENTER)
                .setLoiteringDelay(2000)
                .build()
        }

        fun build(): GeofencingService {

            return GeofencingService(
                context, initialTriggerTimer,
                radiusGeofencing, geofenceList, pendingIntent
            )
        }
    }
}