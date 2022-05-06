package com.example.address.presentation.features.map

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.address.R
import com.example.address.databinding.FragmentMapsBinding
import com.example.address.domain.RegisterAddress
import com.example.address.domain.FullAddressItem
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    var lat: Double = -1.0
    var long: Double = -1.0

    private val args: MapsFragmentArgs by navArgs()
    private val viewModel: MapsViewModel by viewModel()

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.setOnCameraMoveListener {
            lat = googleMap.cameraPosition.target.latitude
            long = googleMap.cameraPosition.target.longitude
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)

        binding.acceptLocationButton.setOnClickListener {
            if (isConnected()) {
                viewModel.sendLocationInfo(
                    RegisterAddress(
                        args.profileInfo.address,
                        args.profileInfo.coordinate_mobile,
                        args.profileInfo.coordinate_phone_number,
                        args.profileInfo.first_name,
                        args.profileInfo.gender,
                        args.profileInfo.last_name,
                        lat,
                        long
                    )
                )
                navigateToAddressPage()
            } else {
                networkNotConnectedError()
            }
        }

        binding.showAddressesList.setOnClickListener {
            findNavController().navigate(
                MapsFragmentDirections.actionMapsFragmentToAddressesFragment()
            )
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is MapsUiState.Success -> showSuccessMessage(uiState.address)
                    is MapsUiState.Failure -> showError()
                }
                showLoadingView(uiState is MapsUiState.Loading)
            }
        }

        return binding.root
    }

    private fun navigateToAddressPage() {
        findNavController().navigate(
            MapsFragmentDirections.actionMapsFragmentToAddressesFragment()
        )
    }

    private fun showLoadingView(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
    }

    private fun showSuccessMessage(address: FullAddressItem) {
        if (address != FullAddressItem())
            Toast.makeText(
                context,
                "موقعیت مکانی شما با مشخضات :\n آدرس : ${address.address} \n و شماره تلفن : ${address.coordinate_mobile} ثبت شد. ",
                Toast.LENGTH_LONG
            )
                .show()

    }

    private fun showError() {
        Snackbar.make(
            binding.root,
            "مشکلی پیش آمده، لطفا مجددا امتحان کرده و یا اتصال اینترنت خودرا بررسی کنید.",
            8000
        )
            .show()
    }

    private fun networkNotConnectedError() {
        Snackbar.make(
            binding.root,
            "لطفا اینترنت خود را فعال کنید.",
            4000
        )
            .show()
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}