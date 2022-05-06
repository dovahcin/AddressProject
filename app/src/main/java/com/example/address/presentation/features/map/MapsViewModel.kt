package com.example.address.presentation.features.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.address.data.InfoRepository
import com.example.address.domain.RegisterAddress
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MapsViewModel(private val infoRepository: InfoRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<MapsUiState> = MutableStateFlow(MapsUiState.Success())
    val uiState: StateFlow<MapsUiState> = _uiState

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _uiState.value = MapsUiState.Failure
    }

    fun sendLocationInfo(registerAddress: RegisterAddress) {
        viewModelScope.launch(exceptionHandler) {
            infoRepository.getLocationInfo(registerAddress)
                .onStart { _uiState.value = MapsUiState.Loading }
                .collect {
                    _uiState.value = MapsUiState.Success(it)
                }
        }
    }
}