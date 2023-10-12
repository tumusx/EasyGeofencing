package com.tumusx.github.easy_geofencing.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tumusx.github.easy_geofencing.data.database.dao.GeoMonitorDAO
import com.tumusx.github.easy_geofencing.data.entity.GeoMonitorEntity

@Database(entities = [GeoMonitorEntity::class], version = 1)
abstract class GeoMonitorDataBase : RoomDatabase() {
    abstract fun geoMonitorDao(): GeoMonitorDAO

}