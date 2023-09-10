package com.example.myapplication

import android.app.PendingIntent
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.github.tumusx.easy_permissions.config.routes.openConfigByIntent
import com.github.tumusx.easy_permissions.permissions.EasyPermissions
import com.github.tumusx.easy_permissions.permissions.PermissionsListener
import com.tumusx.github.easy_geofencing.broadcast.GeofencingBroadcast
import com.tumusx.github.easy_geofencing.location.UpdateLocation
import com.tumusx.github.easy_geofencing.service.GeofencingService

class MainActivity : AppCompatActivity() {
    private val easyPermissions = EasyPermissions(this@MainActivity)
    private lateinit var binding: ActivityMainBinding

    companion object {
        private val permissionsArray = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestPermissions()
        onListenersPermissions()
        setContentView(binding.root)
        configureButtonListeners()
    }

    private fun configureButtonListeners() {
        binding.btnGeofencing.setOnClickListener {
            Toast.makeText(this@MainActivity, "It's starting service... await.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun onListenersPermissions() {
        object : PermissionsListener {
            override fun onErrorPermission() {
                this@MainActivity.openConfigByIntent(
                    Settings.ACTION_APPLICATION_SETTINGS,
                    this@MainActivity.packageName
                )
            }

            override fun onRejectPermission() {
                Toast.makeText(
                    this@MainActivity,
                    "You need accept permissions for use the application",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onSuccessPermission() {
                if (VERSION.SDK_INT >= Build.VERSION_CODES.Q && easyPermissions.checkPermissions(
                        android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ).not()
                ) {
                    easyPermissions.requestPermissions(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                }
            }
        }
    }

    private fun requestPermissions() {
        if (easyPermissions.checkPermissions(permissionsArray).not()) {
            easyPermissions.requestPermissions(permissionsArray)
        } else {
            UpdateLocation(this@MainActivity, 200, 2f)
            val location = Location("").also {
                it.latitude = -26.252245
                it.longitude = -26.252245
            }
            val intent = Intent(this, GeofencingBroadcast::class.java)
            GeofencingService.Builder().setRadius(200f).setInitialTrigger(5)
                .addGeofence("1", location)
                .setPendingIntent(
                    PendingIntent.getBroadcast(
                        this@MainActivity,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                ).addGeofencingClient(context = this@MainActivity).build()
        }
    }
}