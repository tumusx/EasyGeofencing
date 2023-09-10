package com.tumusx.github.easy_geofencing.service

import android.content.Context
import android.location.Location
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

/**
 * @author Murillo Alves
 * @see <a href="https://developer.android.com/training/location/geofencing">Android Documentation</a>
 */
class GeofencingService private constructor(
    context: Context?,
    initialTriggerTimer: Int,
    locationGeofencing: Location?,
    radiusGeofencing: Float
) {
    class Builder {
        private var context: Context? = null
        private var initialTriggerTimer: Int = 0
        private var locationGeofencing: Location? = null
        private var radiusGeofencing = 0F

        private fun setLocation(location: Location): Builder {
            this.locationGeofencing = location
            return this
        }

        fun setRadius(radius: Float): Builder {
            this.radiusGeofencing = radius
            return this
        }

        fun setContext(context: Context): Builder {
            this.context = context
            return this
        }


        fun setInitialTrigger(triggerTime: Int): Builder {
            this.initialTriggerTimer = triggerTime
            return this
        }

        fun build(): GeofencingService {
            return GeofencingService(
                context, initialTriggerTimer,
                locationGeofencing, radiusGeofencing
            )
        }
    }
}