package com.innocent.growingdeveloperclickergame.main

import android.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innocent.growingdeveloperclickergame.databinding.ClickEffectBinding


class ClickEffect (
    private val x: Float,
    private val y: Float,
    private val codingPower: Int) {

    fun render(layoutInflater: LayoutInflater, parentViewGroup: ViewGroup) {
        val clickEffect = ClickEffectBinding.inflate(layoutInflater).root
        clickEffect.x = x - 10f
        clickEffect.y = y - 70f
        clickEffect.text = codingPower.toString()
        parentViewGroup.addView(clickEffect)

        val translationYAnimation: PropertyValuesHolder = PropertyValuesHolder.ofFloat(
            View.TRANSLATION_Y, clickEffect.y - 100f)
        val alphaAnimation: PropertyValuesHolder = PropertyValuesHolder.ofFloat(
            View.ALPHA, 0f)

        ObjectAnimator.ofPropertyValuesHolder(clickEffect, alphaAnimation).apply {
            startDelay = 200
            duration = 500
            start()
        }

        ObjectAnimator.ofPropertyValuesHolder(clickEffect, translationYAnimation).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    parentViewGroup.removeView(clickEffect)
                }
            })
            duration = 500
            start()
        }
    }
}