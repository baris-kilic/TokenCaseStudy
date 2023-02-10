package com.example.last

//Data class for Get QR for Sale Api response
data class ApiResponse(
    val QRdata: String,
    val returnCode: Int,
    val returnDesc: String
)