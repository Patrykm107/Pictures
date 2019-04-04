package com.example.pictures.logic

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.pictures.R
import com.squareup.picasso.Picasso


class MainAdapter(private val values: ArrayList<Entry>, private val currContex: Context) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

        override fun getItemCount(): Int = values.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_element_layout, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentItem = values[position]
            holder.nameText?.text = currentItem.name
            holder.dateText?.text = currentItem.date
            Picasso.get().load(currentItem.pictureUrl).into(holder.pictureView)


            for (i in 0..2) {
                if (i >= currentItem.tags.size) break
                else holder.tagsText?.append(" ${currentItem.tags[i]}")
            }
        }

    fun addItem(item: Entry) {
            values.add(item)
            this.notifyItemInserted(values.size - 1)
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var nameText: TextView? = null
            var pictureView: ImageView? = null
            var dateText: TextView? = null
            var tagsText: TextView? = null

            init {
                nameText = itemView.findViewById(R.id.cardNameText)
                pictureView = itemView.findViewById(R.id.cardPictureView)
                dateText = itemView.findViewById(R.id.cardDateText)
                tagsText = itemView.findViewById(R.id.cardTagsText)
            }
        }

}