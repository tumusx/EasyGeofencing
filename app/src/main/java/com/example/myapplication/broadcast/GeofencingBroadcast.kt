package com.example.myapplication.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class GeofencingBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) {
            return
        }

        if (intent.action.equals(GEOFENCING_ACTION)) {

        }
    }


    companion object {
        const val GEOFENCING_ACTION = "GEOFENCING_ACTION"
    }
}