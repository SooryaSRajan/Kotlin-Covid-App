package com.example.covidapp.model

import java.io.Serializable

data class StateDataClass(
    var stateName: String,
    var stateCode: String,
    var districtData: MutableList<DistrictDataClass>
) : Serializable {
    override fun toString() = "State: $stateName, State Code: $stateCode"

}