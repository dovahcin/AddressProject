package com.example.address.presentation.features.address

import com.example.address.domain.FullAddressItem

sealed class AddressUiState {
    data class Success(val address: MutableList<FullAddressItem> = mutableListOf()): AddressUiState()
    object  Failure: AddressUiState()
    object Loading: AddressUiState()
}