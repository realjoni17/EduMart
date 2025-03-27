package com.joni.edumart.data.api.dto.paymentdto

data class SendPaymentSuccessEmailRequest(
    val orderId: String,
    val paymentId: String,
    val amount: Int
)