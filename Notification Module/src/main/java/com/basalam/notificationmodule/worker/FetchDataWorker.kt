package com.basalam.notificationmodule.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.basalam.notificationmodule.core.NotificationCore
import com.basalam.notificationmodule.repository.NotificationRepository
import com.basalam.notificationmodule.utils.DataState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

import java.lang.Exception

@HiltWorker
class FetchDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationRepository: NotificationRepository,
    private val notificationCore: NotificationCore
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val endPoint = inputData.getString(NotificationCore.ENDPOINT_REQUEST)
        val token = inputData.getString(NotificationCore.TOKEN)
        val deviceId = inputData.getString(NotificationCore.DEVICE_ID)
        val packageName = inputData.getString(NotificationCore.PACKAGE_NAME)
        val className = inputData.getString(NotificationCore.CLASS_NAME)
        val notificationImage = inputData.getInt(
            NotificationCore.NOTIFICATION_IMAGE,
            androidx.appcompat.R.drawable.abc_btn_check_material
        )

        if (endPoint != null && token != null && deviceId != null) {
            getData(endPoint, token, deviceId, notificationImage, packageName, className)
        }

        val outputData = Data.Builder()
            .putString(NotificationCore.NOTIFICATION_DATA, "Hi Da")
            .build()

        return Result.success(outputData)
    }

    private suspend fun getData(
        endPoint: String,
        token: String,
        deviceId: String,
        notificationImage: Int,
        packageName: String?,
        className: String?
    ) {
        notificationRepository.getNotification(endPoint, token, deviceId)
            .catch {
                println("OH DAMN IT, WE GOT ERROR: ${it.message}")
            }
            .collect { notificationRes ->
                when (notificationRes) {
                    is DataState.Success -> {
                        try {
                            val response = notificationRes.data?.get(0)?.asJsonObject

                            val id: String =
                                if (response?.has("id") == true)
                                    response.get("id").toString().replace("\"", "")
                                else
                                    "1"

                            val title: String =
                                if (response?.has("title") == true)
                                    response.get("title").toString().replace("\"", "")
                                else
                                    "باسلام"

                            val content: String =
                                if (response?.has("content") == true)
                                    response.get("content").toString().replace("\"", "")
                                else
                                    "باسلام"

                            val clickReferrerEndPoint: String =
                                if (response?.has("click_referrer") == true)
                                    response.get("click_referrer").toString().replace("\"", "")
                                else
                                    "https://automation.basalam.com/api/api_v1.0/notifications/push/read/{notification_id}"

                            val data = response?.getAsJsonObject("data")

                            notificationCore.sendOnDefaultChannel(
                                applicationContext,
                                id,
                                notificationImage,
                                data,
                                title,
                                content,
                                packageName!!,
                                className!!,
                                clickReferrerEndPoint
                            )
                        } catch (e: Exception) {
                            println("OH DAMN IT, WE GOT ERROR: ${e.message}")
                        }
                    }
                    else ->  println("OH DAMN IT, WE GOT SERVER ERROR")
                }
            }
    }
}