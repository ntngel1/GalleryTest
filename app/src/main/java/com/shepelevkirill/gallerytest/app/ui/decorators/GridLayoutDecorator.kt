package com.shepelevkirill.gallerytest.app.ui.decorators

import android.graphics.Rect
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class GridLayoutDecorator(private val fragment: Fragment): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildLayoutPosition(view)

        // All values in dp
        val leftElementsLeftMargin = (18 * fragment.resources.displayMetrics.density).toInt()
        val leftElementsRightMargin = (9 * fragment.resources.displayMetrics.density).toInt()

        val rightElementsLeftMargin = (9 * fragment.resources.displayMetrics.density).toInt()
        val rightElementsRightMargin = (18 * fragment.resources.displayMetrics.density).toInt()

        val topElementsTopMargin = (17 * fragment.resources.displayMetrics.density).toInt()

        val bottomElementsBottomMargin = (17 * fragment.resources.displayMetrics.density).toInt()

        // left element margin
        if (position % 2 == 0) {
            outRect.left = leftElementsLeftMargin
            outRect.right = leftElementsRightMargin
        }

        // right element margin
        if (position % 2 != 0) {
            outRect.left = rightElementsLeftMargin
            outRect.right = rightElementsRightMargin
        }

        // Top margin
        if (position != 0 && position != 1) {
            outRect.top = topElementsTopMargin
        }


        // Last row bottom margin
        if (parent.adapter!!.itemCount % 2 == 0) // if last row is full (consists of two elements)
            if (position == parent.adapter!!.itemCount - 2 || position == parent.adapter!!.itemCount - 1)
                outRect.bottom = bottomElementsBottomMargin

        if (parent.adapter!!.itemCount % 2 != 0) // if last row isn't full (consists of one element)
            if (position == parent.adapter!!.itemCount - 1)
                outRect.bottom = bottomElementsBottomMargin
    }
}