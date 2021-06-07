package com.github.marcherdiego.shapedreveal.widgets

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff.Mode.CLEAR
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.core.content.ContextCompat
import kotlin.math.pow
import kotlin.math.sqrt

internal class CircularReveal @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val transparentPaint = Paint()

    private var targetRadius = 0f
    private var radius = 0f
    private var interpolator: Interpolator = AccelerateDecelerateInterpolator()
    private var duration = 3000L
    private var onCompletedListener: () -> Unit = {}

    init {
        transparentPaint.color = ContextCompat.getColor(context, android.R.color.transparent)
        transparentPaint.xfermode = PorterDuffXfermode(CLEAR)
        post {
            targetRadius = sqrt((width / 2).toDouble().pow(2.0) + ((height / 2).toDouble().pow(2.0))).toFloat()
        }
    }

    fun setRevealDuration(duration: Long): CircularReveal {
        this.duration = duration
        return this
    }

    fun setInterpolator(interpolator: Interpolator): CircularReveal {
        this.interpolator = interpolator
        return this
    }

    fun setOnCompletedListener(onCompletedListener: () -> Unit): CircularReveal {
        this.onCompletedListener = onCompletedListener
        return this
    }

    fun startReveal(): CircularReveal {
        animate()
            .setDuration(duration)
            .setInterpolator(interpolator)
            .setUpdateListener {
                radius = it.animatedFraction * targetRadius
                invalidate()
            }
            .setListener(object : AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    onCompletedListener()
                }

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationRepeat(animation: Animator?) {}
            })
            .start()
        return this
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, transparentPaint)
        super.onDraw(canvas)
    }
}
