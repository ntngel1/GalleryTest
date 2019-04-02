package com.shepelevkirill.gallerytest.app.ui.decorators

import android.graphics.Rect
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

// TODO Remove magic numbers
class GridLayoutDecorator(private val fragment: Fragment): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildLayoutPosition(view)

        // left element margin
        if (position % 2 == 0) outRect.right = (9 * fragment.resources.displayMetrics.density).toInt()

        // right element margin
        if (position % 2 != 0) outRect.left = (9 * fragment.resources.displayMetrics.density).toInt()

        // Top margin
        if (position != 0 && position != 1) outRect.top = (17 * fragment.resources.displayMetrics.density).toInt()

        // Last row bottom margin
        if (parent.adapter!!.itemCount % 2 == 0) // if last row is full (consists of two elements)
            if (position == parent.adapter!!.itemCount - 2 || position == parent.adapter!!.itemCount - 1)
                outRect.bottom = (17 * fragment.resources.displayMetrics.density).toInt()

        if (parent.adapter!!.itemCount % 2 != 0) // if last row isn't full (consists of one element)
            if (position == parent.adapter!!.itemCount - 1)
                outRect.bottom = (17 * fragment.resources.displayMetrics.density).toInt()
    }
}