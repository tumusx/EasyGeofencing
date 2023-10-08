package com.tumusx.github.easy_geofencing.data.entity

data class GeoMonitorEntity(
    var idGeoMonitor: Long = 0,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var accuracy: Double = 0.0
)