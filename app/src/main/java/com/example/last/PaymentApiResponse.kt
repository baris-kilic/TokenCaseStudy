package com.example.last

//Data class for Payment Api response
data class PaymentApiResponse(
    val applicationID: String,
    val posID: String,
    val returnCode: Int,
    val returnDesc: String,
    val sessionID: String
)