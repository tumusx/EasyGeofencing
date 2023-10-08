package com.tumusx.github.easy_geofencing.data.repository.mappers

import com.tumusx.github.easy_geofencing.data.entity.GeoMonitorEntity
import com.tumusx.github.easy_geofencing.data.repository.model.GeoMonitors

object GeoMapper {
    fun toEntityList(geoVos: List<GeoMonitors>): List<GeoMonitorEntity> {
        return geoVos.map {
            GeoMonitorEntity(
                latitude = it.latitude,
                longitude = it.longitude,
                accuracy = it.accuracy
            )
        }
    }

    fun toVoList(geoEntities: List<GeoMonitorEntity>): List<GeoMonitors> {
        return geoEntities.map {
            GeoMonitors(
                idGeoMonitor = it.idGeoMonitor,
                latitude = it.latitude,
                longitude = it.longitude,
                accuracy = it.accuracy
            )
        }
    }
}