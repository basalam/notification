Basalam Notification
========

A library to receive and show notiffication for Android.

 * Register or unregister user to your server.
 * Receive Notifictation per 15 minutes.
 * Show notification as soon as received.
 * Handle on notification click.

Download
--------

Add the package dependencies to your build.gradle file:
```kotlin
    dependencies {
       implementation 'com.basalam.notificationmodule:notification:1.0.0'
    }
```

Add the repository to your build.gradle file:
```kotlin
repositories {
    maven {
            url = uri("https://maven.pkg.github.com/basalam/notification")
        }
}
```
How to use
--------

Your application class:

```kotlin
@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()
}
```
add App to manifest:
```xml
        android:name=".App"
```

Your MainActivity class:

```kotlin
        @Inject
        lateinit var notificationCore: NotificationCore
```

To init library:
```kotlin
        notificationCore.init(application = application, owner = this)
```

To Create Worker:
```kotlin
        notificationCore.createWorker(
            application,
            token = "token",
            endPoint = "https://example.basalam.com/api/api_v1.0/notifications/",
            deviceId = "test",
            notificationImage = R.drawable.basalam,
            notificationPackageName = "com.basalam.notification",
            notificationClassPackageName = "com.basalam.notification.MainActivity"
        )
```

To cancel Worker:
```kotlin
        notificationCore.cancelWorker(application)
```

To register user:
```kotlin
        notificationCore.registerUser(
            token = "token",
            endPoint = "https://example.basalam.com/api/api_v1.0/notifications/register",
            deviceId = "test",
            isCustomerApp = true
        )
```

To unregister user:
```kotlin
        notificationCore.unregisterUser(
            token = "token",
            endPoint = "https://example.basalam.com/api/api_v1.0/notifications/unregister",
            deviceId = "test",
            isCustomerApp = true
        )
```

To handle on notification click:
```kotlin
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
                        token = "token",
                        id = id
                    )
                }
            }
        }
    }
```

