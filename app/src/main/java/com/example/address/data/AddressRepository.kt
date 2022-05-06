package com.example.address.data

import com.example.address.data.network.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AddressRepository(private val services: ApiServices) {
    fun getAddress() = flow {
        emit(services.getAddressInformation())
    }.flowOn(Dispatchers.IO)
}