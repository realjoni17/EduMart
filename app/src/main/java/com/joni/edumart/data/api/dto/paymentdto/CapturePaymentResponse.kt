package com.joni.edumart.data.api.dto.paymentdto

data class CapturePaymentResponse(
    val id: String, // Razorpay order ID
    val amount: Int,
    val currency: String
)