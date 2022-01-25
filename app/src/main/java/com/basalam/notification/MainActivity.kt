package com.basalam.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.basalam.notificationmodule.core.core.NotificationCore
import dagger.hilt.android.AndroidEntryPoint
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
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiMGNiZDkwYzhmMDM4NWNmZjgzZmMxNjJkMDg3ZWMyNDc2MThmMjk3MmIyYjFmMTlkMTYxY2MxYWNkZTg4ZjNhY2I0ODZjZGYwNGQ5Mjc0YjIiLCJpYXQiOjE2Mzc4NDM2ODIuMDY0MzIxLCJuYmYiOjE2Mzc4NDM2ODIuMDY0MzI4LCJleHAiOjE2NDU2MTk2ODIuMDA4NzQ0LCJzdWIiOiI4MTgyMiIsInNjb3BlcyI6W119.Ldx8vgQnlmhzU_LoHY0YBHkyVLultVMscAChBjjltljb6LkpY-8MMT1gy8yB9HLfX9LyUExuRmCbnLhpJhU-IjbWzqR7Mc4iBkb-UOJffTZDnp7woVfk3C_5G1EpDW8fCoFLIivb9XDFM7XRU-ZJmuRNBM4DKAudM_wDx1qZZr5jbh-MD56_wCQJsKiUnCFCNo5WTV6A4g1CNbmndU0CfXgHegKR0_HkmsFE_PhedSw4NbOD96aDsT8yw6P-LAz5juHO3y7Un0L13e5Kk-UT5dVCxo09_YAQ52WW9qs_NVYOmNWHxOXUQZ7I87VxW79BoNbTGhhfAlwJqHal0gonjc52TWRxaIFU_F8sP82YxxPsizxpKX-kh055zIwLigFweQMJmdl6SPajverI737Aww-dApQVuXaY6HgJ2iV2qbw-pfn2SdKdsGRotnjT4CqfLp7UhWyDMtYpMv3QnK78L6eXObGeUu2k9vjTgiWrLGd0RtA39fyWhQwjpc0LmdsyIcu3XJrjSrQh-5Q9fMNtqdzt5qwDF_IHvbXRiJvqIwdm8v8YH6IpWikt8gQrico57_CKbxbchwGex6WWPCwU0m12EcedE7HHsoW_cL3AYyJHDZ4Ax6IpEPCCTaDis5022lZl9XZhueQ0y9WiMU5r-Lr96EPWo6TI22pG_JUSXsc"
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