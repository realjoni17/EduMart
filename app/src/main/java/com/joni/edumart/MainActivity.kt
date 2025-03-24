package com.joni.edumart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.joni.edumart.common.Constant
import com.joni.edumart.presentation.PaymentViewModel
import com.joni.edumart.screens.navigation.AppNavigation
import com.joni.edumart.ui.theme.EduMartTheme
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultListener {
    private val viewModel: PaymentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EduMartTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                  AppNavigation()
                    //EnrolledCoursesScreen()
                }
            }
        }
    }

    override fun onPaymentSuccess(paymentId: String) {
        val currentState = viewModel.paymentState.value
        if (currentState is PaymentViewModel.PaymentState.OrderCreated) {
            val orderId = currentState.data.id
            val amount = currentState.data.amount

            // Verify Payment
            viewModel.verifyPayment(
                token = Constant.TOKEN,
                razorpayOrderId = orderId,
                razorpayPaymentId = paymentId,
                razorpaySignature = "", // Get from Razorpay callback if available
                courseIds = listOf("67c8793c30282fcf69462f8f")
            )

            // Send Payment Success Email After Verification
            viewModel.sendPaymentSuccessEmail(
                token = Constant.TOKEN,
                orderId = orderId,
                paymentId = paymentId,
                amount = amount
            )
        }
    }

    override fun onPaymentError(code: Int, response: String?) {
        viewModel.setErrorState("Payment Failed: $response")
    }

}