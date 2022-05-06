package com.example.address.presentation.features.address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.address.R
import com.example.address.databinding.FragmentAddressesBinding
import com.example.address.domain.FullAddressItem
import com.example.address.presentation.features.address.adapter.AddressAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddressFragment : Fragment() {
    private var _binding: FragmentAddressesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddressViewModel by viewModel()

    private val addressAdapter = AddressAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_addresses, container, false)

        viewModel.getAddress()

        binding.recyclerAddress.adapter = addressAdapter

        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is AddressUiState.Success -> updateView(uiState.address)
                    is AddressUiState.Failure -> showError()
                }
                showLoadingView(uiState is AddressUiState.Loading)
            }
        }

        return binding.root
    }

    private fun updateView(address: MutableList<FullAddressItem>) {
        addressAdapter.update(address)
    }

    private fun showError() {
        Snackbar.make(
            binding.root,
            "مشکلی پیش آمده، لطفا مجددا امتحان کنید یا اتصال اینترنت خود را بررسی کنید.",
            8000
        )
            .show()
    }

    private fun showLoadingView(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}