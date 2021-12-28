package com.example.covidapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.covidapp.model.DistrictDataClass
import com.example.covidapp.model.StateDataClass
import kotlinx.android.synthetic.main.activity_information.*

class InformationActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        val districtData: DistrictDataClass =
            intent.getSerializableExtra("DISTRICT DATA") as DistrictDataClass

        supportActionBar?.title = districtData.districtName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        districtName.text = districtData.districtName
        activeCase.text = "Active Cases: ${districtData.active}"
        confirmedCase.text = "Confirmed Cases: ${districtData.confirmed}"
        deceasedCase.text = "Deceased Cases: ${districtData.deceased}"
        recoveredCases.text = "Recovered Cases: ${districtData.recovered}"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}