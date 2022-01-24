package com.basalam.notificationmodule.service

import com.basalam.notificationmodule.model.RegisterModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import javax.inject.Inject

class RemoteHelperImpl @Inject constructor(
    private val remoteService: RemoteService
) : RemoteHelper {
    override suspend fun getNotification(
        endPoint: String,
        authorization: String,
        deviceId: String
    ): JsonArray? = remoteService.getNotification(endPoint, authorization, deviceId)

    override suspend fun registerUser(
        endPoint: String,
        authorization: String,
        registerModel: RegisterModel
    ): JsonObject? = remoteService.registerUser(endPoint, authorization, registerModel)

    override suspend fun unregisterUser(
        endPoint: String,
        authorization: String,
        registerModel: RegisterModel
    ): JsonObject? = remoteService.unregisterUser(endPoint, authorization, registerModel)

    override suspend fun clickedOnNotification(
        endPoint: String,
        authorization: String,
        id: String
    ): JsonObject? = remoteService.clickedOnNotification(endPoint, authorization, id)

}