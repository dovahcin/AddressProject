package com.example.address.presentation.features.map

import com.example.address.domain.FullAddressItem

sealed class MapsUiState {
    data class Success(val address: FullAddressItem = FullAddressItem()): MapsUiState()
    object Failure: MapsUiState()
    object Loading: MapsUiState()
}