package com.example.pictures.layout

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.pictures.R


class MainAdapter(private val values: ArrayList<String>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

        override fun getItemCount(): Int = values.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_element_layout, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.nameText?.text = values[position]
            //holder.bmiText?.text = entry.bmiVal
            //entry=values[i]
            //set text into views
        }

        fun addItem(item: String){
            values.add(item)
            this.notifyItemInserted(values.size - 1)
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            //var bmiText: TextView? = null
            //assign views into variables
            var nameText: TextView? = null

            init {
                nameText = itemView.findViewById(R.id.NameText)
                //bmiText = itemView.findViewById(R.id.bmiHistory)

            }
        }

}