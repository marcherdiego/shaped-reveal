package com.github.marcherdiego.shapedreveal.widgets

import android.animation.Animator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff.Mode.CLEAR
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.core.content.ContextCompat
import com.github.marcherdiego.shapedreveal.listeners.SimpleAnimatorListener
import kotlin.math.pow
import kotlin.math.sqrt

internal class CircularReveal @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val ripplePaint = Paint()
    private val transparentPaint = Paint()

    private var targetRadius = 0f
    private var radius = 0f
    private var interpolator: Interpolator = AccelerateDecelerateInterpolator()
    private var duration = 3000L
    private var onRippleCompletedListener: () -> Unit = {}
    private var onCompletedListener: () -> Unit = {}

    private var state = State.IDLE

    init {
        ripplePaint.color = Color.RED
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

    internal fun setOnRippleCompletedListener(onRippleCompletedListener: () -> Unit): CircularReveal {
        this.onRippleCompletedListener = onRippleCompletedListener
        return this
    }

    fun startReveal(): CircularReveal {
        state = State.EXPANDING
        animate()
            .setDuration(duration / 2)
            .setInterpolator(interpolator)
            .setUpdateListener {
                radius = it.animatedFraction * targetRadius
                invalidate()
            }
            .setListener(object : SimpleAnimatorListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    onRippleCompletedListener()
                    startCollapsing()
                }
            })
            .start()
        return this
    }

    private fun startCollapsing() {
        state = State.COLLAPSING
        radius = 0f
        animate()
            .setDuration(duration / 2)
            .setInterpolator(interpolator)
            .setUpdateListener {
                radius = it.animatedFraction * targetRadius
                invalidate()
            }
            .setListener(object : SimpleAnimatorListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    state = State.IDLE
                    onCompletedListener()
                }
            })
            .start()
    }

    override fun onDraw(canvas: Canvas?) {
        when (state) {
            State.EXPANDING -> canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, ripplePaint)
            State.COLLAPSING -> {
                canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), targetRadius, ripplePaint)
                canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, transparentPaint)
            }
        }
        super.onDraw(canvas)
    }

    enum class State {
        IDLE, EXPANDING, COLLAPSING
    }
}
