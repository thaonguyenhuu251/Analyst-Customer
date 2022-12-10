package com.htnguyen.customeranalysis.interfaces

import com.htnguyen.customeranalysis.model.CustomerRequest
import com.htnguyen.customeranalysis.model.CustomerResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("/predict")
    fun sendReq(@Body requestModel: CustomerRequest) : Call<CustomerResponse>
}