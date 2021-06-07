package com.github.marcherdiego.shapedreveal.listeners

import android.animation.Animator
import android.animation.Animator.AnimatorListener

open class SimpleAnimatorListener : AnimatorListener {
    override fun onAnimationStart(animation: Animator?) {}

    override fun onAnimationEnd(animation: Animator?) {}

    override fun onAnimationCancel(animation: Animator?) {}

    override fun onAnimationRepeat(animation: Animator?) {}
}
