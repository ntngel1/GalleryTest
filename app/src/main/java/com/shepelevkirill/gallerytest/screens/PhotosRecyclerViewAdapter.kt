package com.shepelevkirill.gallerytest.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.core.Photos
import com.squareup.picasso.Picasso

class PhotosRecyclerViewAdapter(private val parent: Photos.View): RecyclerView.Adapter<PhotosRecyclerViewAdapter.ViewHolder>() {
    private val data: ArrayList<PhotoModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item_photo, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.count()
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.ui_image)

        fun bind(photo: PhotoModel) {
            TODO("GET CONTENT FULL URL")
            Picasso.get()
                .load(photo.image.contentUrl)
                .into(imageView)
            imageView.setOnClickListener {
                parent.openPhoto(photo)
            }
        }
    }
}