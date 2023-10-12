package com.tumusx.github.easy_geofencing.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("locations")
data class GeoMonitorEntity(
    @PrimaryKey var idGeoMonitor: Long = 0,
    @ColumnInfo("latitude") var latitude: Double = 0.0,
    @ColumnInfo("longitude") var longitude: Double = 0.0,
    @ColumnInfo("accuracy") var accuracy: Double = 0.0
)