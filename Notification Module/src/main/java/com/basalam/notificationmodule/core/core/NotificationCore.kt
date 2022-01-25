package com.basalam.notificationmodule.core.core

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.work.*
import com.basalam.notificationmodule.core.viewmodel.NotificationViewModel
import com.basalam.notificationmodule.core.worker.FetchDataWorker
import com.google.gson.JsonObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationCore @Inject constructor() {

    private var notificationViewModel: NotificationViewModel? = null

    companion object {
        const val Channel_ID_DEFAULT: String = "Channel_ID_DEFAULT"
        const val NOTIFICATION_DATA: String = "NOTIFICATION_DATA"
        const val ENDPOINT_REQUEST: String = "ENDPOINT_REQUEST"
        const val TOKEN: String = "TOKEN"
        const val DEVICE_ID: String = "DEVICE_ID"
        const val NOTIFICATION_EXTRA: String = "NOTIFICATION_EXTRA"
        const val NOTIFICATION_ID: String = "NOTIFICATION_ID"
        const val NOTIFICATION_CLICK_ENDPOINT: String = "NOTIFICATION_CLICK_ENDPOINT"
        const val NOTIFICATION_IMAGE: String = "NOTIFICATION_IMAGE"
        const val NOTIFICATION_CLICK_DATA_EXTRA: String = "NOTIFICATION_CLICK_EXTRA"
        const val PACKAGE_NAME: String = "PACKAGE_NAME"
        const val CLASS_NAME: String = "CLASS_NAME"
    }

    fun init(
        application: Application,
        owner: ViewModelStoreOwner
    ) {
        createNotificationDefaultChannel(application)
        notificationViewModel = ViewModelProvider(owner)[NotificationViewModel::class.java]
    }

    fun createWorker(
        application: Application,
        token: String,
        endPoint: String,
        deviceId: String,
        notificationImage: Int,
        notificationPackageName: String,
        notificationClassPackageName: String,
    ) {
        val workManager = WorkManager.getInstance(application.applicationContext!!)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(false)
            .build()

        val data = Data.Builder()
        data.putString(ENDPOINT_REQUEST, endPoint)
        data.putString(TOKEN, token)
        data.putString(DEVICE_ID, deviceId)
        data.putInt(NOTIFICATION_IMAGE, notificationImage)
        data.putString(PACKAGE_NAME, notificationPackageName)
        data.putString(CLASS_NAME, notificationClassPackageName)

        val work = PeriodicWorkRequestBuilder<FetchDataWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        workManager
            .enqueue(work)
    }

    private fun createNotificationDefaultChannel(
        application: Application
    ) {
        application.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel =
                    NotificationChannel(Channel_ID_DEFAULT, "Channel_Default", importance).apply {
                        description = "This is default channel"
                    }
                val notificationManager: NotificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun sendOnDefaultChannel(
        context: Context,
        notificationId: String,
        notificationImage: Int,
        data: JsonObject?,
        notificationTitle: String,
        notificationContent: String,
        notificationPackageName: String,
        notificationClassPackageName: String,
        clickReferrerEndPoint: String,
    ) {
        val componentName = ComponentName(notificationPackageName, notificationClassPackageName)

        val intent = Intent()
        intent.component = componentName
        intent.putExtra(NOTIFICATION_EXTRA, true)
        intent.putExtra(NOTIFICATION_ID, notificationId)
        intent.putExtra(NOTIFICATION_CLICK_ENDPOINT, clickReferrerEndPoint)
        intent.putExtra(NOTIFICATION_CLICK_DATA_EXTRA, data.toString())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val notifyPendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat
            .Builder(context, Channel_ID_DEFAULT)
            .setSmallIcon(notificationImage)
            .setContentTitle(notificationTitle)
            .setContentText(notificationContent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(notifyPendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId.toInt(), builder.build())
        }
    }

    fun registerUser(
        token: String,
        endPoint: String,
        deviceId: String,
        isCustomerApp: Boolean = true
    ) = notificationViewModel?.registerUser(token, endPoint, deviceId, isCustomerApp)

    fun unregisterUser(
        token: String,
        endPoint: String,
        deviceId: String,
        isCustomerApp: Boolean = true
    ) = notificationViewModel?.unregisterUser(token, endPoint, deviceId, isCustomerApp)

    fun clickedOnNotification(
        endPoint: String,
        token: String,
        id: String
    ) = notificationViewModel?.clickedOnNotification(endPoint, token, id)
}