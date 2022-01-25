package com.basalam.notificationmodule.data.repository

import com.basalam.notificationmodule.domain.model.RegisterModel
import com.basalam.notificationmodule.data.service.RemoteHelper
import com.basalam.notificationmodule.common.DataState
import com.basalam.notificationmodule.domain.repository.NotificationRepository
import com.google.gson.JsonArray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val remoteHelper: RemoteHelper
) : NotificationRepository {
    override fun getNotification(
        endPoint: String,
        authorization: String,
        deviceId: String
    ): Flow<DataState<JsonArray?>> = flow {
        try {
            val response = remoteHelper.getNotification(endPoint, authorization, deviceId)
            emit(DataState.Success(response!!))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.catch {
        emit(DataState.Error(Exception(it)))
    }

    override fun registerUser(
        endPoint: String,
        authorization: String,
        deviceId: String,
        isCustomerApp: Boolean
    ): Flow<DataState<Boolean>> = flow {
        try {
            val res = remoteHelper.registerUser(
                endPoint,
                authorization,
                RegisterModel(deviceId, isCustomerApp)
            )
            emit(DataState.Success(res?.get("result")?.asBoolean!!))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.catch {
        emit(DataState.Error(Exception(it)))
    }

    override fun unregisterUser(
        endPoint: String,
        authorization: String,
        deviceId: String,
        isCustomerApp: Boolean
    ): Flow<DataState<Boolean>> = flow {
        try {
            val res = remoteHelper.unregisterUser(
                endPoint,
                authorization,
                RegisterModel(deviceId, isCustomerApp)
            )
            emit(DataState.Success(res?.get("result")?.asBoolean!!))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.catch {
        emit(DataState.Error(Exception(it)))
    }

    override fun clickedOnNotification(
        endPoint: String,
        authorization: String,
        id: String
    ): Flow<DataState<Boolean>> = flow {
        try {
            val res = remoteHelper.clickedOnNotification(
                endPoint,
                authorization,
                id
            )
            emit(DataState.Success(res?.get("result")?.asBoolean!!))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.catch {
        emit(DataState.Error(Exception(it)))
    }
}