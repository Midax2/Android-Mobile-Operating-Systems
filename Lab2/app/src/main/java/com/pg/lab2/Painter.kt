package com.pg.lab2

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.graphics.Paint

class Painter : View {
    private var isBaseColors = true
    private val paint = Paint()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun setColorBasedOnCondition(isColorChanged: Boolean) {
        isBaseColors = isColorChanged
        invalidate()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        if (isBaseColors) {
            paint.setARGB(255, 255, 0, 0)
        } else {
            paint.setARGB(255, 0, 0, 255)
        }

        canvas.drawCircle(100.0f, (height / 2).toFloat(), 100f, paint)

        if (isBaseColors) {
            paint.setARGB(255, 0, 255, 0)
        } else {
            paint.setARGB(255, 255, 0, 0)
        }
        canvas.drawRect(
            width.toFloat() / 2, height.toFloat() / 3,
            width.toFloat(), height.toFloat(), paint
        )
    }
}
