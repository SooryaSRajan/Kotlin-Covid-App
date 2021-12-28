package com.example.covidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.covidapp.adapter.StateAdapter
import com.example.covidapp.model.DistrictDataClass
import com.example.covidapp.model.StateDataClass
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var TAG = "Main Activity";
    var stateAdapter : StateAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val queue = Volley.newRequestQueue(this)
        val url = "https://data.covid19india.org/state_district_wise.json"
        val listOfStateData =
            mutableListOf<StateDataClass>()
        recyclerView.layoutManager = LinearLayoutManager(this)
        stateAdapter = StateAdapter(listOfStateData, this)
        recyclerView.adapter = stateAdapter

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                run {
                    for (i in response.keys()) {
                        val stateObject: JSONObject =
                            response.get(i) as JSONObject; //Gets object for each state
                        val districtDataObject: JSONObject =
                            stateObject.get("districtData") as JSONObject; //Gets district data for each state

                        val listOfDistrictData =
                            mutableListOf<DistrictDataClass>(); //List to store each district

                        for (j in districtDataObject.keys()) {
                            val individualDistrictData: JSONObject =
                                districtDataObject.get(j) as JSONObject; //Each district object
                            listOfDistrictData.add(
                                DistrictDataClass(
                                    districtName = j,
                                    active = individualDistrictData.getInt("active"),
                                    confirmed = individualDistrictData.getInt("confirmed"),
                                    deceased = individualDistrictData.getInt("deceased"),
                                    recovered = individualDistrictData.getInt("recovered")

                                )
                            )
                        }

                        listOfStateData.add(
                            StateDataClass(
                                stateName = i,
                                stateCode = stateObject.getString("statecode"),
                                districtData = listOfDistrictData
                            )
                        )
                        stateAdapter?.notifyItemInserted(listOfStateData.size - 1)

                    }
                }
            },
            { error ->
                Log.e(TAG, "onCreate: $error")
            }
        )

        queue.add(jsonObjectRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val menuItem = menu?.findItem(R.id.action_search)
        val searchView : SearchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                stateAdapter?.filter?.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                stateAdapter?.filter?.filter(query)
                return false
            }

        })

        return true
    }
}