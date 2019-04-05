package com.shepelevkirill.gallerytest.app.ui.adapters

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.gallerytest.domain.gateway.PhotoGateway
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.app.ui.scenes.photos.PhotosView
import com.shepelevkirill.gallerytest.app.ui.view.RatioImageView
import com.shepelevkirill.gallerytest.app.utils.loadThumbnail
import com.shepelevkirill.gallerytest.app.utils.stopLoading
import javax.inject.Inject

class PhotosAdapter(private val parent: PhotosView) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    @Inject
    lateinit var photoGateway: PhotoGateway

    private lateinit var recyclerView: RecyclerView
    private val viewHolders = HashMap<Int, ViewHolder>()
    private val highlight = HashMap<Int, Boolean>()

    val data: ArrayList<PhotoModel> = ArrayList()

    init {
        App.appComponent.inject(this)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item_photo, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.adapterDataIndex = position
        val photoModel = data[position]
        viewHolders[photoModel.id] = holder
        holder.bind(photoModel)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        viewHolders.remove(holder.adapterDataIndex)
        holder.cleanup()
    }

    fun addPhotoModel(photo: PhotoModel) {
        data.add(photo)
        notifyDataSetChanged()
    }

    fun addPhotos(photos: List<PhotoModel>) {
        data.addAll(photos)
        notifyDataSetChanged()
    }

    fun clearPhotoModels() {
        data.clear()
        notifyDataSetChanged()
    }

    fun highlightPhotoWithId(id: Int) {
        highlight[id] = true
        val listener = object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
        recyclerView.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var adapterDataIndex: Int = -1
        var isHighlighted: Boolean = false
            set(value) {
                imageView.isHighlighted = value
                field = value
            }

        private val imageView: RatioImageView = view.findViewById(R.id.ui_image)
        private lateinit var photo: PhotoModel
        fun bind(photo: PhotoModel) {
            if (highlight[photo.id] != null) {
                imageView.isHighlighted = true
            }
            this.photo = photo
            val url = photoGateway.getPhotoUrl(photo.image.contentUrl)
            imageView.loadThumbnail(url)
            imageView.setOnClickListener {
                highlight.clear()
                isHighlighted = false
                this@PhotosAdapter.notifyItemChanged(adapterDataIndex)
                parent.onPhotoClicked(photo)
            }
        }

        fun cleanup() {
            imageView.isHighlighted = false
            imageView.stopLoading()
        }
    }
}