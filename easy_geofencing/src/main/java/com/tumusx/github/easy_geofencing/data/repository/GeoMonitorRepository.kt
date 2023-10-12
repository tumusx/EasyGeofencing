package com.tumusx.github.easy_geofencing.data.repository

import com.tumusx.github.easy_geofencing.data.database.GeoMonitorDataBase
import com.tumusx.github.easy_geofencing.data.database.exceptions.DatabaseException
import com.tumusx.github.easy_geofencing.data.repository.mappers.GeoMapper
import com.tumusx.github.easy_geofencing.data.repository.model.GeoMonitors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class GeoMonitorRepository(private val appDatabase: GeoMonitorDataBase) {
    fun getAllDestinations(): Flow<LocalState<List<GeoMonitors>>> = flow {
        try {
            this@GeoMonitorRepository.appDatabase.geoMonitorDao().selectLocations()
                .onEach { geoMonitors ->
                    emit(LocalState.Success(GeoMapper.toVoList(geoMonitors)))
                }
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(LocalState.Error("Error in data processing"))
        }
    }

    fun insertLocations(list: List<GeoMonitors>) {
        flow<Boolean> {
            GeoMapper.toEntityList(list).forEach(action = {
                appDatabase.geoMonitorDao().insertLocation(it)
            })
        }.catch { error ->
            error.printStackTrace()
        }
    }

    fun clearDatabase() {
        try {
            this.appDatabase.clearAllTables()
        } catch (exception: Exception) {
            exception.printStackTrace()
            throw DatabaseException(exception.message ?: exception.stackTraceToString())
        }
    }
}


sealed class LocalState<T>(val result: T? = null, val messageError: String = "") {
    data class Success<T>(val data: T?) : LocalState<T>(result = data)
    data class Error<T>(val errorMessage: String) : LocalState<T>(messageError = errorMessage)
}