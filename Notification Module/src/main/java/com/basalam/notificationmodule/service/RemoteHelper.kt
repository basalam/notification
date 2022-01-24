package com.basalam.notificationmodule.service

import com.basalam.notificationmodule.model.RegisterModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject

interface RemoteHelper {
    suspend fun getNotification(
        endPoint: String,
        authorization: String,
        deviceId: String
    ): JsonArray?

    suspend fun registerUser(
        endPoint: String,
        authorization: String,
        registerModel: RegisterModel
    ): JsonObject?

    suspend fun unregisterUser(
        endPoint: String,
        authorization: String,
        registerModel: RegisterModel
    ): JsonObject?

    suspend fun receivedNotification(
        endPoint: String,
        authorization: String,
        id: String
    ): JsonObject?

    suspend fun clickedOnNotification(
        endPoint: String,
        authorization: String,
        id: String
    ): JsonObject?
}