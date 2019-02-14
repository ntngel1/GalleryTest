package com.shepelevkirill.gallerytest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.gallerytest.server.response.photos.Image
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cardview_item_photo.view.*
import java.lang.RuntimeException

class PhotosRecyclerViewAdapter :
    RecyclerView.Adapter<PhotosRecyclerViewAdapter.ViewHolder>() {


    val images: ArrayList<Image> = ArrayList() // Dataset
    var onPhotoClickedListener: ((Image) -> Int)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item_photo, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val imageView: ImageView = itemView.ui_image
        private var image: Image? = null

        fun bind(image: Image) {
            this.image = image
            Picasso.get()
                .load(image.image!!.getFullUrl())
                .resize(180, 128) // TODO Adaptive image resizing based on screen resolution
                .centerCrop()
                .into(imageView)

            this.itemView.setOnClickListener {
                onPhotoClickedListener?.invoke(image) ?: throw RuntimeException("OnPhotoClicked callback isn't defined!")
            }
        }
    }
}
