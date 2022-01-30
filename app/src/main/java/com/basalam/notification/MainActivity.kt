package com.basalam.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.basalam.notificationmodule.core.core.NotificationCore
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var notificationCore: NotificationCore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationCore.init(application = application, owner = this)

        notificationCore.createWorker(
            application,
            token = getToken(),
            endPoint = "https://automation.basalam.com/api/api_v1.0/notifications/push/users/test",
            deviceId = "test",
            notificationImage = R.drawable.ic_logo_type,
            notificationPackageName = "com.basalam.notification",
            notificationClassPackageName = "com.basalam.notification.MainActivity"
        )

//        notificationCore.cancelWorker(application)

//        notificationCore.registerUser(
//            token = getToken(),
//            endPoint = "https://automation.basalam.com/api/api_v1.0/devices",
//            deviceId = "test",
//            isCustomerApp = true
//        )
//
//        notificationCore.unregisterUser(
//            token = getToken(),
//            endPoint = "https://automation.basalam.com/api/api_v1.0/devices",
//            deviceId = "test",
//            isCustomerApp = true
//        )
    }

    private fun getToken(): String {
        return "**"
    }

    override fun onResume() {
        super.onResume()
        checkIntent(intent)
    }

    private fun checkIntent(intent: Intent?) {
        intent?.let {
            if (it.hasExtra(NotificationCore.NOTIFICATION_EXTRA)) {
                val endPoint = it.getStringExtra(NotificationCore.NOTIFICATION_CLICK_ENDPOINT)
                val id = it.getStringExtra(NotificationCore.NOTIFICATION_ID)

                if (endPoint != null && id != null) {
                    notificationCore.clickedOnNotification(
                        endPoint = endPoint,
                        token = getToken(),
                        id = id
                    )
                }
            }
        }
    }
}