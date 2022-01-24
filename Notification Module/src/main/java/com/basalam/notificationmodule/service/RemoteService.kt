package com.basalam.notificationmodule.service

import com.basalam.notificationmodule.model.RegisterModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.http.*

interface RemoteService {
    @GET
    suspend fun getNotification(
        @Url endPoint: String,
        @Header("authorization") authorization: String,
        @Query("device_id") deviceId: String
    ): JsonArray?

    @POST
    suspend fun registerUser(
        @Url endPoint: String,
        @Header("authorization") authorization: String,
        @Body registerModel: RegisterModel
    ): JsonObject?

    @PUT
    suspend fun unregisterUser(
        @Url endPoint: String,
        @Header("authorization") authorization: String,
        @Body registerModel: RegisterModel
    ): JsonObject?

    @GET
    suspend fun clickedOnNotification(
        @Url endPoint: String,
        @Header("authorization") authorization: String,
        @Query("notification_id") id: String
    ): JsonObject?
}