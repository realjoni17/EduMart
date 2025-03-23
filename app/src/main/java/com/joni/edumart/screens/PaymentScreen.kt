package com.joni.edumart.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joni.edumart.data.api.dto.paymentdto.CapturePaymentResponse
import com.joni.edumart.presentation.PaymentViewModel
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject


@SuppressLint("ContextCastToActivity")
@Composable
fun PaymentScreen(
    courseIds: List<String>,
    token: String,
    viewModel: PaymentViewModel = hiltViewModel()
) {


    val context = LocalContext.current as Activity
    val paymentState by viewModel.paymentState

    LaunchedEffect(paymentState) {
        if (paymentState is PaymentViewModel.PaymentState.OrderCreated) {
            val data = (paymentState as PaymentViewModel.PaymentState.OrderCreated).data
            startRazorpayPayment(context, data, viewModel, token, courseIds)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { viewModel.capturePayment(token, courseIds) },
            enabled = paymentState !is PaymentViewModel.PaymentState.Loading
        ) {
            Text("Pay for Courses")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (paymentState) {
            is PaymentViewModel.PaymentState.Loading -> CircularProgressIndicator()
           // is PaymentViewModel.PaymentState.Verifying -> Text("Verifying Payment...")
          //  is PaymentViewModel.PaymentState.OrderCreated -> Text("Payment Initiated")
          //  is PaymentViewModel.PaymentState.Verified -> Text("Payment Verified")
          //  is PaymentViewModel.PaymentState.EmailSent -> Text("Payment Complete - Email Sent")
            is PaymentViewModel.PaymentState.Error -> Text("Error: ${(paymentState as PaymentViewModel.PaymentState.Error).message}")
            else -> Text("Ready to Pay")
        }
    }
}

fun startRazorpayPayment(
    activity: Activity,
    data: CapturePaymentResponse,
    viewModel: PaymentViewModel,
    token: String,
    courseIds: List<String>
) {
    val checkout = Checkout()
    checkout.setKeyID("your_razorpay_key_id") // Replace with your Razorpay Key ID

    try {
        val options = JSONObject().apply {
            put("name", "Your App Name")
            put("description", "Course Payment")
            put("order_id", data.id)
            put("currency", data.currency)
            put("amount", data.amount)
            put("prefill", JSONObject().apply {
                put("email", "user@example.com") // Replace with user email
                put("contact", "1234567890") // Replace with user contact
            })
        }

        checkout.open(activity, options)
    } catch (e: Exception) {
        viewModel.paymentState = PaymentViewModel.PaymentState.Error(e.message ?: "Razorpay error")
    }

    // Implement PaymentResultListener in your Activity
    activity as? PaymentResultListener ?: throw IllegalStateException("Activity must implement PaymentResultListener")
}