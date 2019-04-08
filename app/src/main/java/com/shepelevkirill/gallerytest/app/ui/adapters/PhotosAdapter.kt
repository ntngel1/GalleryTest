package com.shepelevkirill.gallerytest.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.app.ui.view.RatioImageView
import com.shepelevkirill.gallerytest.app.utils.loadThumbnail
import com.shepelevkirill.gallerytest.app.utils.stopLoading
import com.shepelevkirill.gallerytest.domain.gateway.PhotoGateway
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import javax.inject.Inject

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    @Inject
    lateinit var photoGateway: PhotoGateway
    var onPhotoClickedListener: ((photo: PhotoModel) -> Unit)? = null

    private lateinit var recyclerView: RecyclerView
    private val highlight = HashSet<Int>()

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
        holder.bind(photoModel)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.cleanup()
    }

    fun addPhotoModel(photo: PhotoModel) {
        data.add(photo)
        notifyDataSetChanged()
    }

    fun addPhotoModels(photos: List<PhotoModel>) {
        data.addAll(photos)
        notifyDataSetChanged()
    }

    fun clearPhotoModels() {
        data.clear()
        highlight.clear()
        notifyDataSetChanged()
    }

    fun highlightPhoto(id: Int) {
        highlight.add(id)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var photoModel: PhotoModel
        var adapterDataIndex: Int = -1
        private val imageView: RatioImageView = view.findViewById(R.id.ui_image)

        fun bind(photoModel: PhotoModel) {
            this.photoModel = photoModel

            if (highlight.contains(photoModel.id)) {
                highlight(true)
            }

            val url = photoGateway.getPhotoUrl(photoModel.image.contentUrl)
            imageView.loadThumbnail(url)
            imageView.setOnClickListener {
                highlight(false)
                onPhotoClickedListener?.invoke(photoModel)
            }
        }

        private fun highlight(isEnabled: Boolean) {
            val globalLayoutListener = object: ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    this@PhotosAdapter.notifyItemChanged(adapterDataIndex)
                    recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }

            if (!isEnabled) {
                highlight.remove(photoModel.id)
            }

            if (isEnabled != imageView.isHighlighted) {
                imageView.isHighlighted = isEnabled
                recyclerView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
            }
        }

        fun cleanup() {
            imageView.isHighlighted = false
            imageView.stopLoading()
        }
    }
}