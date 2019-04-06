package com.example.pictures.logic

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.pictures.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.squareup.picasso.Picasso
import java.text.DateFormat.getDateTimeInstance
import java.util.*





class MainAdapter(private val values: ArrayList<Entry>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    companion object {
        const val MAX_TAG_AMOUNT = 3
    }

    override fun getItemCount(): Int = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_element_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = values[position]
        holder.nameText?.text = currentItem.name
        holder.dateText?.text = getDateTimeInstance().format(currentItem.date)
        var tags:Array<String> = arrayOf()

        val target = object : com.squareup.picasso.Target {

            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                val labeler = FirebaseVision.getInstance().onDeviceImageLabeler
                val image = FirebaseVisionImage.fromBitmap(bitmap)

                labeler.processImage(image)
                    .addOnSuccessListener { labels ->
                        var i = 0
                        for (label in labels) {
                            if(i < MAX_TAG_AMOUNT) i++
                            else break
                            holder.tagsText?.append(" ${label.text}")
                        }
                    }
                    .addOnFailureListener {
                        Log.wtf("HELP",it.message)
                    }
                holder.pictureView?.setImageBitmap(bitmap)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
        }
        holder.pictureView?.tag = target
        Picasso.get().load(currentItem.pictureURL).into(target)
    }

    fun addItem(newEntry: Entry) {
        values.add(newEntry)
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