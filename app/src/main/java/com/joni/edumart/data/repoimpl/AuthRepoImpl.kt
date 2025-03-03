package com.joni.edumart.data.repoimpl

import com.joni.edumart.data.api.ApiService
import com.joni.edumart.domain.repository.AuthRepo
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(private val apiService: ApiService) : AuthRepo{
}