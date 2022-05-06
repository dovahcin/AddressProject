package com.example.address.domain

data class CityObject(
    val city_id: Int = -1,
    val city_name: String = ""
 )
data class FullAddressItem(
    val address: String = "",
    val address_id: String = "",
    val coordinate_mobile: String = "",
    val coordinate_phone_number: String = "",
    val first_name: String = "",
    val gender: String = "",
    val id: Int = -1,
    val last_name: String = "",
    val lat: Double = -1.0,
    val lng: Double = -1.0,
    val region: Region = Region()
)
data class Region(
    val city_object: CityObject = CityObject(),
    val id: Int = -1,
    val name: String = "",
    val state_object: StateObject = StateObject()
)
data class StateObject(
    val state_id: Int = -1,
    val state_name: String = ""
 )