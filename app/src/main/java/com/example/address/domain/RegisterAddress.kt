package com.example.address.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterAddress(
    val address: String = "",
    val coordinate_mobile: String = "",
    val coordinate_phone_number: String = "",
    val first_name: String = "",
    val gender: String = "",
    val last_name: String = "",
    val lat: Double = -1.0,
    val lng: Double = -1.0,
    val region: Int = 1
) : Parcelable