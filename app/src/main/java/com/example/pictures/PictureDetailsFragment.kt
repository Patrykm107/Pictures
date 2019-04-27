package com.example.pictures


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_picture_details.view.*
import kotlinx.android.synthetic.main.fragment_similar_pictures.view.*
import java.text.DateFormat
import java.util.*


class PictureDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val transaction = childFragmentManager.beginTransaction()
        val upperDetails = PictureUpperDetailsFragment()
        upperDetails.arguments = arguments
        transaction.replace(R.id.upperFragmentContainer, upperDetails)

        val similarPicsFragment = SimilarPicturesFragment()

        similarPicsFragment.arguments = arguments
        transaction.replace(R.id.bottomFragmentContainer, similarPicsFragment)

        transaction.commit()

        return inflater.inflate(R.layout.details_layout, container, false)
    }

}

class PictureUpperDetailsFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_picture_details, container, false)
        view.pictureNameText.text = arguments!!.getString(DetailsActivity.picNameKey)
        val date = Date()
        date.time = arguments!!.getLong(DetailsActivity.picDateKey)
        view.picDateText.text = DateFormat.getDateTimeInstance().format(date)
        view.picTagsText.append(" "+arguments!!.getStringArrayList(DetailsActivity.picTagsKey).toString())
        return view
    }
}

class SimilarPicturesFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_similar_pictures, container, false)
        val similarPicsUrl = arguments!!.getStringArrayList(DetailsActivity.similarPicsUrlKey)
        var tag = 1
        for (url in similarPicsUrl) {
            //Picasso.get().load(url).into(view.imageView)
            tag++

            val target = object : com.squareup.picasso.Target {

                override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                    view.imageView.setImageBitmap(bitmap)
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
            }
            Picasso.get().load(url).into(target)
        }
        return view
    }
}
