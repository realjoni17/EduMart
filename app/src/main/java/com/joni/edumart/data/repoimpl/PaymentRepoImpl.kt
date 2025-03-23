package com.joni.edumart.data.repoimpl

import com.joni.edumart.data.api.ApiService
import com.joni.edumart.data.api.dto.paymentdto.CapturePaymentRequest
import com.joni.edumart.data.api.dto.paymentdto.CapturePaymentResponse
import com.joni.edumart.data.api.dto.paymentdto.SendPaymentSuccessEmailRequest
import com.joni.edumart.data.api.dto.paymentdto.VerifyPaymentRequest
import com.joni.edumart.domain.repository.PaymentRepo
import javax.inject.Inject

 class PaymentRepoImpl @Inject constructor(
        private val apiService: ApiService
 ) : PaymentRepo {
     override suspend fun CapturePayment(
         token: String,
         courseIds: List<String>
     ): CapturePaymentResponse {
         val response = apiService.capturePayment(token, CapturePaymentRequest(courseIds))
         try {
             if (response.isSuccessful) {
                 return response.body()!!
             } else {
                 throw Exception(response.errorBody().toString())
             }
         } catch (e: Exception) {
             throw Exception(e.message)
         }

     }

     override suspend fun VerifyPayment(
         token: String,
         razorpayPaymentId: String,
         razorpayOrderId: String,
         razorpaySignature: String,
         courseIds: List<String>
     ): Any {
         val respone = apiService.verifyPayment(
             token,
             VerifyPaymentRequest(razorpayPaymentId, razorpayOrderId, razorpaySignature, courseIds)
         )
         try {
             if (respone.isSuccessful) {
                 return respone.body()!!.data!!
             } else {
                 throw Exception(respone.errorBody().toString())
             }
         } catch (e: Exception) {
             throw Exception(e.message)
         }
     }

     override suspend fun SendPaymentSuccessEmail(
         token: String,
         paymentId: String,
         orderId: String,
         amount: Int
     ): Any {
         val response = apiService.sendPaymentSuccessEmail(
             token,
             SendPaymentSuccessEmailRequest(paymentId, orderId, amount)
         )
         try {
             if (response.isSuccessful) {
                 return response.body()!!.data!!
             } else {
                 throw Exception(response.errorBody().toString())
             }
         } catch (e: Exception) {
             throw Exception(e.message)
         }
     }
 }