package com.joni.edumart.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joni.edumart.data.api.dto.paymentdto.CapturePaymentResponse
import com.joni.edumart.domain.repository.PaymentRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepo: PaymentRepo
) : ViewModel() {

    private val _paymentState = MutableStateFlow<PaymentState>(PaymentState.Idle)
    var paymentState: StateFlow<PaymentState> = _paymentState.asStateFlow()

    fun capturePayment(token: String, courseIds: List<String>) {
        viewModelScope.launch {
            _paymentState.value = PaymentState.Loading
            try {
                val response = paymentRepo.CapturePayment(token, courseIds)
                _paymentState.value = PaymentState.OrderCreated(response)
            } catch (e: Exception) {
                _paymentState.value = PaymentState.Error(e.message)
            }
        }
    }

    fun verifyPayment(
        token: String,
        razorpayPaymentId: String,
        razorpayOrderId: String,
        razorpaySignature: String,
        courseIds: List<String>
    ) {
        viewModelScope.launch {
            _paymentState.value = PaymentState.Loading
            try {
                val response = paymentRepo.VerifyPayment(
                    token, razorpayPaymentId, razorpayOrderId, razorpaySignature, courseIds
                )
                _paymentState.value = PaymentState.Success(response)
            } catch (e: Exception) {
                _paymentState.value = PaymentState.Error(e.message)
            }
        }
    }

    fun sendPaymentSuccessEmail(token: String, paymentId: String, orderId: String, amount: Int) {
        viewModelScope.launch {
            _paymentState.value = PaymentState.Loading
            try {
                val response = paymentRepo.SendPaymentSuccessEmail(token, paymentId, orderId, amount)
                _paymentState.value = PaymentState.Success(response)
            } catch (e: Exception) {
                _paymentState.value = PaymentState.Error(e.message)
            }
        }
    }
    fun setErrorState(message: String) {
        _paymentState.value = PaymentState.Error(message)
    }


    sealed class PaymentState {
        object Idle : PaymentState()
        object Loading : PaymentState()
        data class Success(val data: Any) : PaymentState()
        data class Error(val message: String?) : PaymentState()
        data class OrderCreated(val data: CapturePaymentResponse) : PaymentState()
    }
}

