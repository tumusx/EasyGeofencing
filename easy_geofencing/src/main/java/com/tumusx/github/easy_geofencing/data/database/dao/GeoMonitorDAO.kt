package com.tumusx.github.easy_geofencing.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tumusx.github.easy_geofencing.data.entity.GeoMonitorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GeoMonitorDAO {

    @Insert
    fun insertLocation(vararg geoMonitors: GeoMonitorEntity)

    @Query("SELECT * FROM LOCATIONS")
    fun selectLocations() : Flow<List<GeoMonitorEntity>>

    @Query("SELECT * FROM LOCATIONS WHERE idGeoMonitor")
    fun selectById() : Flow<GeoMonitorEntity>


}