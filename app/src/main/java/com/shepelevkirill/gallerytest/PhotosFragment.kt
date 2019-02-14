package com.shepelevkirill.gallerytest

import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.gallerytest.server.response.photos.GetPhotosResponse
import com.shepelevkirill.gallerytest.server.response.photos.Image
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_photos.*
import kotlinx.android.synthetic.main.fragment_photos.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException

abstract class PhotosFragment : Fragment() {

    companion object {
        const val ITEMS_PER_PAGE = 6 // This constant is used for API-requests (getPhotos method)
    }

    private var isLoading = false // Flag - we are sending request to the server now and don't need to send another one
    private var itemsOnServer = -1 // Count of images on server, so it's used for duct tape

    /*
     ContentLoader is just a way to delegate children classes logic of fetching data from a server.
     Every class can have different parameters for requests, so it is obvious choice
      */
    abstract var contentLoader: (RecyclerView) -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Setting title
        view.ui_title.text = arguments?.getString("title") ?: "UNDEFINED"

        // RecyclerView setup
        val recyclerViewAdapter = PhotosRecyclerViewAdapter()
        view.ui_photos.setHasFixedSize(true)
        view.ui_photos.layoutManager = GridLayoutManager(view.context, 2)
        view.ui_photos.adapter = recyclerViewAdapter

        // Setting up scrolling listener
        view.ui_photos.setOnScrollListener(onRecycleViewScrollListener)

        // Settings up onPhotoClicked listener
        recyclerViewAdapter.onPhotoClickedListener = onPhotoClickedListener

        // Specifying margins for elements in RecyclerView
        view.ui_photos.addItemDecoration(recyclerViewMarginsDecorator)

        // SwipeRefreshLayout setup
        ui_swipeRefreshLayout.setOnRefreshListener(onRefreshListener)

        // Load some content
        loadContent()
    }

    // So this callback is separated because it has some same logic for every child-class, but we can override it
    open var requestCallback = object: Callback<GetPhotosResponse> {
        override fun onFailure(call: Call<GetPhotosResponse>, t: Throwable) {
            this@PhotosFragment.onFailure(call, t)
        }
        override fun onResponse(call: Call<GetPhotosResponse>, response: Response<GetPhotosResponse>) {
            this@PhotosFragment.onResponse(call, response)
        }
    }

    /*
     This functions just allows us to determine: is it fetching data now or not
     This method is used in children classes to create ContentLoader
      */
    private fun canLoadContent(recyclerView: RecyclerView): Boolean {
        if (isLoading) {
            ui_swipeRefreshLayout.isRefreshing = false
            return false
        }

        // TODO Fix this duct tape
        /*
         DUCT TAPE!
         So when we calculate page number, we will divide current items count in our dataset by some limit constant,
         and result can be some real number, this little duct tape fixes this problem.
          */
        if (itemsOnServer != -1 && itemsOnServer <= recyclerView.adapter!!.itemCount) {
            return false
        }

        if (ui_network_error_layout.visibility != View.VISIBLE) {
            ui_progressbar?.visibility = View.VISIBLE
        }

        isLoading = true // Started sending request
        return true
    }

    fun loadContent() {
        if (!canLoadContent(ui_photos))
            return

        contentLoader(ui_photos)
    }

    // Basic on request failure callback
    fun onFailure(call: Call<GetPhotosResponse>, t: Throwable) {
        ui_progressbar?.visibility = View.INVISIBLE
        ui_swipeRefreshLayout.isRefreshing = false
        showNetworkErrorScreen()

        isLoading = false // Request sent
    }

    // Basic on request response callback
    fun onResponse(call: Call<GetPhotosResponse>, response: Response<GetPhotosResponse>) {
        val result = response.body()
        val recyclerViewAdapter = ui_photos.adapter as PhotosRecyclerViewAdapter

        ui_progressbar?.visibility = View.INVISIBLE
        ui_swipeRefreshLayout.isRefreshing = false
        ui_network_error_layout.visibility = View.INVISIBLE

        try {
            recyclerViewAdapter.images.addAll(result!!.images!!.toList())
            itemsOnServer = result.totalItems
            ui_photos.adapter!!.notifyDataSetChanged()
        } catch (e: NullPointerException) {
            showNetworkErrorScreen()
            return
        } finally {
            isLoading = false // Request sent
        }
    }

    // Listener for refresh on swipe
    private val onRefreshListener = {
        val recyclerViewAdapter = view!!.ui_photos.adapter as PhotosRecyclerViewAdapter
        recyclerViewAdapter.images.clear()
        recyclerViewAdapter.notifyDataSetChanged()
        loadContent()
    }

    // Listener for click on photo in RecyclerView
    private val onPhotoClickedListener = { image: Image ->
            // Opens PhotoFragment to see details
            activity!!.supportFragmentManager.beginTransaction()
                .add(activity!!.fragment_container.id, PhotoFragment.newInstance(image))
                .addToBackStack(null)
                .commit()
        }


    // Listener for RecycleView scroll
    private val onRecycleViewScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val lastVisibleItemPos = layoutManager.findLastVisibleItemPosition()

            // if we need to load new content
            if (lastVisibleItemPos >= layoutManager.itemCount - 1) {
                loadContent()
            }
        }
    }

    // RecyclerView decorator for items' spacing
    private val recyclerViewMarginsDecorator = object: RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            // TODO Adaptive margins between RecyclerView items

            val position = parent.getChildLayoutPosition(view)

            // Left margin
            if (position % 2 != 0) {
                outRect.left = 17
            }

            // Top margin
            if (position != 0 && position != 1) {
                outRect.top = (17 * 2.5).toInt()
            }

            // Last row bottom margin
            if (position == parent.adapter!!.itemCount || position == parent.adapter!!.itemCount - 1) {
                outRect.bottom = (17 * 2.5).toInt()
            }
        }
    }

    private fun showNetworkErrorScreen() {
        // TODO Show Network Error Screen correctly
        /*
          Clearing images dataset in RecyclerView to make it's area clear
          If we just make it transparent, it will cause non-working SwipeRefreshLayout
        */
        val recyclerViewAdapter = ui_photos.adapter as PhotosRecyclerViewAdapter
        recyclerViewAdapter.images.clear()
        recyclerViewAdapter.notifyDataSetChanged()

        ui_network_error_layout?.visibility = View.VISIBLE
    }
}