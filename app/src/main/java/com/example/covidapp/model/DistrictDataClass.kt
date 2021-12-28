package com.example.covidapp.model

import java.io.Serializable

data class DistrictDataClass(
    var districtName: String,
    var active: Int,
    var confirmed: Int,
    var deceased: Int,
    var recovered: Int
) : Serializable {
    override fun toString() =
        "District: $districtName, Active: $active, Confirmed: $confirmed, Deceased: $deceased, Recovered: $recovered"
}