package com.example.address.data.network

import com.example.address.domain.RegisterAddress
import com.example.address.domain.FullAddressItem
import retrofit2.http.*




interface ApiServices {
    @POST("address")
    suspend fun sendLocationInformation(@Body registerAddress: RegisterAddress): FullAddressItem

    @GET("address")
    suspend fun getAddressInformation(): MutableList<FullAddressItem>
}