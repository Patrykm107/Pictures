package com.example.pictures


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_picture.view.*



class PictureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_picture, container, false)
        val picUrl = arguments?.getString(DetailsActivity.picUrlKey)

        Picasso.get().load(picUrl).into(view.pictureView)

        return view
    }

}
