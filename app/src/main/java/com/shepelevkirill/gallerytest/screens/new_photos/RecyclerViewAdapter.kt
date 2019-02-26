package com.shepelevkirill.gallerytest.screens.new_photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.core.gateway.PhotoGateway
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.App
import com.shepelevkirill.gallerytest.R
import com.squareup.picasso.Picasso
import javax.inject.Inject

class RecyclerViewAdapter(private val parent: NewPhotos.View) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val data: ArrayList<PhotoModel> = ArrayList()
    @Inject lateinit var photoGateway: PhotoGateway

    init {
        App.appComponent.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item_photo, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.count()
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun add(photo: PhotoModel) {
        data.add(photo)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.ui_image)

        fun bind(photo: PhotoModel) {
            val url = photoGateway.getPhotoUrl(photo.image.contentUrl)
            Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .into(imageView)
            imageView.setOnClickListener {
                parent.onPhotoClicked(photo)
            }
        }
    }
}