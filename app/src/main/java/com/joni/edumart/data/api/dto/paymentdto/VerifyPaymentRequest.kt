package com.joni.edumart.data.api.dto.paymentdto

data class VerifyPaymentRequest(
    val razorpay_payment_id: String,
    val razorpay_order_id: String,
    val courses: List<String>
)