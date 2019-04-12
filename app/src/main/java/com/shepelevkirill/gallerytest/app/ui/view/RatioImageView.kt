package com.shepelevkirill.gallerytest.app.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.shepelevkirill.gallerytest.R

class RatioImageView : ImageView {

    var isHighlighted: Boolean = false
    private var heightRatio: Float = 0.0F
    private var widthRatio: Float = 0.0F
    private val viewBounds = Rect()
    private val paint = Paint().apply {
        color = Color.YELLOW
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.STROKE
        strokeWidth = 2.5F * resources.displayMetrics.density
    }


    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(attrs)
    }

    private fun setHeightRatio(ratio: Float) {
        if (ratio != heightRatio) {
            widthRatio = 0.0F
            heightRatio = ratio
            requestLayout()
        }
    }

    private fun setWidthRatio(ratio: Float) {
        if (ratio != widthRatio) {
            heightRatio = 0.0F
            widthRatio = ratio
            requestLayout()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        if (heightRatio > 0.0)
            height = (width * heightRatio).toInt()

        if (widthRatio > 0.0)
            width = (height * widthRatio).toInt()

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hightlight(canvas)
    }

    private fun hightlight(canvas: Canvas?) {
        if (isHighlighted) {
            val density = resources.displayMetrics.density
            getDrawingRect(viewBounds)
            canvas?.drawRoundRect(
                viewBounds.top.toFloat(), viewBounds.left.toFloat(),
                viewBounds.top.toFloat() + width, viewBounds.left.toFloat() + height,
                CORNER_RADIUS_IN_DP * density, CORNER_RADIUS_IN_DP * density,
                paint
            )
        }
    }

    private fun init(attributeSet: AttributeSet?) {
        attributeSet ?: return

        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.RatioImageView)
        val wr = ta.getFloat(R.styleable.RatioImageView_widthRatio, 0.0F)
        val hr = ta.getFloat(R.styleable.RatioImageView_heightRatio, 0.0F)

        if (wr > 0.0F && hr > 0.0F) {
            throw RuntimeException("You should specify only one ratio!")
        }

        setWidthRatio(wr)
        setHeightRatio(hr)
        ta.recycle()
    }


    companion object {
        private const val CORNER_RADIUS_IN_DP = 6
    }
}