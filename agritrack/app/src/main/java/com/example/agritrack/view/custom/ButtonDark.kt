package com.example.agritrack.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.agritrack.R

class ButtonDark @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

    private var txtColor: Int = 0
    private var backgroundColor: Drawable

    init {
        txtColor = ContextCompat.getColor(context, android.R.color.white)
        backgroundColor = ContextCompat.getDrawable(context, R.drawable.button_dark) as Drawable
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = backgroundColor
        setTextColor(txtColor)
    }
}