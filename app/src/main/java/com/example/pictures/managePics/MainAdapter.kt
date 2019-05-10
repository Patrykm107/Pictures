package com.example.pictures.managePics

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
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
import com.example.pictures.DetailsActivity


class MainAdapter(val values: ArrayList<Entry>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    companion object {
        const val MAX_TAG_AMOUNT = 3
        const val MAX_SIMILAR_PICS_AMOUNT = 6
        const val HASH = '#'
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
        holder.pictureView?.setImageResource(R.mipmap.ic_launcher)
        val tags = arrayListOf<String>()

        val target = object : com.squareup.picasso.Target {

            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                val labeler = FirebaseVision.getInstance().onDeviceImageLabeler
                val image = FirebaseVisionImage.fromBitmap(bitmap)

                labeler.processImage(image)
                    .addOnSuccessListener { labels ->
                        var i = 0
                        var tagsText = ""
                        for (label in labels) {
                            tags.add(label.text + "")
                            if (i == MAX_TAG_AMOUNT) continue
                            else tagsText+="$HASH${tags[i]} "
                            i++
                        }
                        holder.tagsText!!.text = tagsText
                    }
                    .addOnFailureListener {
                    }
                holder.pictureView?.setImageBitmap(bitmap)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
        }
        currentItem.tags = tags
        holder.pictureView?.tag = target
        Picasso.get().load(currentItem.pictureURL).into(target)


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.picNameKey, currentItem.name)
            intent.putExtra(DetailsActivity.picTagsKey, currentItem.tags)
            intent.putExtra(DetailsActivity.picDateKey, currentItem.date.time)
            intent.putExtra(DetailsActivity.picUrlKey, currentItem.pictureURL)

            val similarUrl = findSimilarEntries(position, MAX_SIMILAR_PICS_AMOUNT).map { it.pictureURL }
            intent.putExtra(DetailsActivity.similarPicsUrlKey, ArrayList(similarUrl))

            holder.itemView.context.startActivity(intent)
        }
    }

    fun addItem(newEntry: Entry) {
        values.add(newEntry)
        this.notifyItemInserted(values.size - 1)
    }

    fun removeItem(position: Int) {
        values.remove(values[position])
        this.notifyItemRemoved(position)
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

    fun findSimilarEntries(position: Int, amount : Int): ArrayList<Entry> {
        val current = values[position]
        val valuesWithoutCurr = ArrayList<Entry>(values)
        valuesWithoutCurr.remove(current)

        val listWithCount = arrayListOf<Pair<Entry, Int>>()
        for (entry in valuesWithoutCurr) {
            val similarity = current.compareTagsSimilarity(entry)
            if (similarity>0) {
                listWithCount.add(Pair(entry, similarity))
            }
        }

        var sorted = listWithCount.sortedWith(compareBy { it.second }).asReversed()
        val picsToTake = if (sorted.size < amount) sorted.size else amount
        if (sorted.isNotEmpty()) sorted = sorted.subList(0, picsToTake)

        return ArrayList(sorted.map{ it.first })
    }

}