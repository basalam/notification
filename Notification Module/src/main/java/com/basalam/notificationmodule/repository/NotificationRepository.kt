package com.basalam.notificationmodule.repository

import com.basalam.notificationmodule.utils.DataState
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotification(
        endPoint: String,
        authorization: String,
        deviceId: String
    ): Flow<DataState<JsonArray?>>

    fun registerUser(
        endPoint: String,
        authorization: String,
        deviceId: String,
        isCustomerApp:Boolean
    ): Flow<DataState<Boolean>>

    fun unregisterUser(
        endPoint: String,
        authorization: String,
        deviceId: String,
        isCustomerApp:Boolean
    ): Flow<DataState<Boolean>>

    fun receivedNotification(
        endPoint: String,
        authorization: String,
        id: String
    ): Flow<DataState<Boolean>>

    fun clickedOnNotification(
        endPoint: String,
        authorization: String,
        id: String
    ): Flow<DataState<Boolean>>
}