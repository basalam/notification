package com.basalam.notificationmodule.domain.model

import com.google.gson.annotations.SerializedName

data class RegisterModel(
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("is_customer_app")
    val isCustomerApp: Boolean
)
