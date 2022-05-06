package com.example.address.data

import com.example.address.data.network.ApiServices
import com.example.address.domain.RegisterAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class InfoRepository(private val services: ApiServices) {

    fun getLocationInfo(registerAddress: RegisterAddress) = flow {
        emit(sendLocationInfo(registerAddress))
    }.flowOn(Dispatchers.IO)

    private suspend fun sendLocationInfo(registerAddress: RegisterAddress) =
        services.sendLocationInformation(registerAddress)

}