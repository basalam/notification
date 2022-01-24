package com.basalam.notificationmodule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.basalam.notificationmodule.repository.NotificationRepository
import com.basalam.notificationmodule.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    fun registerUser(
        token: String,
        endPoint: String,
        deviceId: String,
        isCustomerApp: Boolean = true
    ): Flow<DataState<Boolean>> = notificationRepository.registerUser(
        endPoint = endPoint,
        authorization = token,
        deviceId = deviceId,
        isCustomerApp = isCustomerApp
    ).shareIn(CoroutineScope(Dispatchers.IO), SharingStarted.Eagerly, 1)

    fun unregisterUser(
        token: String,
        endPoint: String,
        deviceId: String,
        isCustomerApp: Boolean = true
    ): Flow<DataState<Boolean>> = notificationRepository.unregisterUser(
        endPoint = endPoint,
        authorization = token,
        deviceId = deviceId,
        isCustomerApp = isCustomerApp
    ).shareIn(CoroutineScope(Dispatchers.IO), SharingStarted.Eagerly, 1)

    fun receivedNotification(
        endPoint: String,
        token: String,
        id: String
    ): Flow<DataState<Boolean>> = notificationRepository.receivedNotification(
        endPoint = endPoint,
        authorization = token,
        id = id
    ).shareIn(CoroutineScope(Dispatchers.IO), SharingStarted.Eagerly, 1)

    fun clickedOnNotification(
        endPoint: String,
        token: String,
        id: String
    ): Flow<DataState<Boolean>> = notificationRepository.clickedOnNotification(
        endPoint = endPoint,
        authorization = token,
        id = id
    ).shareIn(CoroutineScope(Dispatchers.IO), SharingStarted.Eagerly, 1)
}