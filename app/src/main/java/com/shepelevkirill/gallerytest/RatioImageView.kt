package com.shepelevkirill.gallerytest

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView

class RatioImageView(context: Context, attributeSet: AttributeSet) : ImageView(context, attributeSet) {
    private var heightRatio: Double = 0.0
    private var widthRatio: Double = 0.0

    fun setHeightRatio(ratio: Double) {
        if (ratio != heightRatio) {
            widthRatio = 0.0
            heightRatio = ratio
            requestLayout()
        }
    }

    fun setWidthRatio(ratio: Double) {
        if (ratio != widthRatio) {
            heightRatio = 0.0
            widthRatio = ratio
            requestLayout()
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        if (heightRatio > 0.0)
            height = (width * heightRatio).toInt()

        if (widthRatio > 0.0)
            width = (height * widthRatio).toInt()

        setMeasuredDimension(width, height)

        Log.d("MEASURED FOR IMAGEVIEW ${this.hashCode()}", "w $width h $height")
    }

}