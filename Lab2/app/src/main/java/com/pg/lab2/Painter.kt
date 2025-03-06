package com.pg.lab2

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.graphics.Paint

class Painter : View {
    private var isRedColor = true // Default color state
    private val paint = Paint()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    // Method to update the color dynamically
    fun setColorBasedOnCondition(isRed: Boolean) {
        isRedColor = isRed
        invalidate() // Triggers a redraw of the View
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        // Set circle color based on the boolean flag
        if (isRedColor) {
            paint.setARGB(255, 255, 0, 0) // Red
        } else {
            paint.setARGB(255, 0, 0, 255) // Blue
        }

        // Draw circle
        canvas.drawCircle(100.0f, (height / 2).toFloat(), 100f, paint)

        // Set rectangle color (optional, you can change based on another condition)
        if (isRedColor) {
            paint.setARGB(255, 0, 255, 0) // Green
        } else {
            paint.setARGB(255, 255, 0, 0) // Red
        }
        canvas.drawRect(
            width.toFloat() / 2, height.toFloat() / 3,
            width.toFloat(), height.toFloat(), paint
        )
    }
}
