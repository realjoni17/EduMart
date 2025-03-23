package com.joni.edumart.domain.repository

import com.joni.edumart.data.api.dto.paymentdto.CapturePaymentResponse

interface PaymentRepo {

    suspend fun CapturePayment(token : String, courseIds : List<String>) : CapturePaymentResponse


    suspend fun VerifyPayment(token: String,
                              razorpayPaymentId: String,
                              razorpayOrderId: String,
                              razorpaySignature: String,
                              courseIds: List<String>)  : Any


    suspend fun SendPaymentSuccessEmail(token: String,
                                        paymentId : String,
                                        orderId : String,
                                        amount : Int) : Any
}