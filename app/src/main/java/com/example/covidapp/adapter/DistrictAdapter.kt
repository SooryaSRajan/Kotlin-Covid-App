package com.example.covidapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covidapp.DistrictDataActivity
import com.example.covidapp.InformationActivity
import com.example.covidapp.R
import com.example.covidapp.model.DistrictDataClass
import com.example.covidapp.model.StateDataClass
import java.util.*

class DistrictAdapter(
    private var list: MutableList<DistrictDataClass>,
    private val context: Context
) :
    RecyclerView.Adapter<DistrictAdapter.DataHolder>(), Filterable {

    var listUnFiltered = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_box, parent, false)
        return DataHolder(view)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.textView.text = list[position].districtName
    }

    override fun getItemCount(): Int = list.size


    inner class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.name)

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, InformationActivity::class.java)
                val dataClass : DistrictDataClass = list[adapterPosition]
                intent.putExtra("DISTRICT DATA", dataClass)
                context.startActivity(intent)
            }
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                list = if (charString.isEmpty()) listUnFiltered else {
                    val filteredList = mutableListOf<DistrictDataClass>()
                    listUnFiltered
                        .filter {
                            (it.districtName.lowercase(Locale.getDefault()).contains(constraint.toString()
                                .lowercase(Locale.getDefault())))
                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = list }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                list = if (results?.values == null)
                    mutableListOf()
                else
                    results.values as MutableList<DistrictDataClass>
                notifyDataSetChanged()
            }
        }
    }
}