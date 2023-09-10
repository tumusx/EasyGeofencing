package com.tumusx.github.easy_geofencing.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

class GeofencingBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val geofencingEvent = intent?.let { GeofencingEvent.fromIntent(it) }
        if (geofencingEvent?.hasError() == true) {
            Toast.makeText(
                context,
                "Error: ${GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)}",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val alertString = "Geofence Alert :" +
                " Trigger ${geofencingEvent?.triggeringGeofences}" +
                " Transition ${geofencingEvent?.geofenceTransition}"
        Log.d(
            GeofencingBroadcast::class.simpleName,
            alertString
        )

        Toast.makeText(context, alertString, Toast.LENGTH_LONG).show()

    }
}