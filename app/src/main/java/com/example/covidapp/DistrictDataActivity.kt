package com.example.covidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidapp.adapter.DistrictAdapter
import com.example.covidapp.model.DistrictDataClass
import com.example.covidapp.model.StateDataClass
import kotlinx.android.synthetic.main.activity_district_data.*

class DistrictDataActivity : AppCompatActivity() {
    var districtAdapter : DistrictAdapter? = null
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_district_data)

        val stateData: StateDataClass = intent.getSerializableExtra("STATE DATA") as StateDataClass

        supportActionBar?.title = stateData.stateName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listOfDistrictData = stateData.districtData
        recyclerView.layoutManager = LinearLayoutManager(this)
        districtAdapter = DistrictAdapter(listOfDistrictData, this)
        recyclerView.adapter = districtAdapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val menuItem = menu?.findItem(R.id.action_search)
        val searchView : SearchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                districtAdapter?.filter?.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                districtAdapter?.filter?.filter(query)
                return false
            }

        })

        return true
    }
}