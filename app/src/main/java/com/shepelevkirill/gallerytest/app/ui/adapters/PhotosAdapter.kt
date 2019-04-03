package com.shepelevkirill.gallerytest.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.gallerytest.domain.gateway.PhotoGateway
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.app.ui.scenes.photos.PhotosView
import com.shepelevkirill.gallerytest.app.utils.loadThumbnail
import com.shepelevkirill.gallerytest.app.utils.stopLoading
import javax.inject.Inject

class PhotosAdapter(private val parent: PhotosView) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    @Inject lateinit var photoGateway: PhotoGateway

    val data: ArrayList<PhotoModel> = ArrayList()

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
    override fun onViewRecycled(holder: ViewHolder) = holder.cleanup()

    fun addPhotoModel(photo: PhotoModel) {
        data.add(photo)
        notifyDataSetChanged()
    }

    fun clearPhotoModels() {
        data.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.ui_image)

        fun bind(photo: PhotoModel) {
            val url = photoGateway.getPhotoUrl(photo.image.contentUrl)
            imageView.loadThumbnail(url)
            imageView.setOnClickListener {
                parent.onPhotoClicked(photo)
            }
        }

        fun cleanup() {
            imageView.stopLoading()
        }
    }
}