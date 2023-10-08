package com.tumusx.github.easy_geofencing.data.repository

import android.util.Log
import com.tumusx.github.easy_geofencing.data.repository.mappers.GeoMapper
import com.tumusx.github.easy_geofencing.data.repository.model.GeoMonitors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GeoMonitorRepository {

    fun getAllDestinations() : Flow<List<GeoMonitors>> = flow {
        try{
            //TODO doing with real data at next weekend
            emit(GeoMapper.toVoList(listOf()))
        }catch (exception: Exception) {
            UtilLog.debug(GeoMonitorRepository::class.java.name, exception.stackTraceToString())
        }
    }
}


object UtilLog {
    fun debug(name: String, messageError: String) {
       if(BuildConfig.debug) {
           Log.d(name, messageError)
       }
    }
}