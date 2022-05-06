package com.example.address.presentation.features.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.address.data.AddressRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AddressViewModel(private val addressRepository: AddressRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<AddressUiState>(AddressUiState.Success())
    val uiState: StateFlow<AddressUiState> = _uiState

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _uiState.value = AddressUiState.Failure
    }

    fun getAddress() {
        viewModelScope.launch(exceptionHandler) {
            addressRepository.getAddress()
                .onStart { _uiState.value = AddressUiState.Loading }
                .collect {
                    _uiState.value = AddressUiState.Success(it)
                }
        }
    }
}