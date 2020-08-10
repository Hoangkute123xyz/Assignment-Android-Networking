package com.hoangpro.assignmentandroidnetworking.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AnimationSet
import androidx.core.animation.addListener

class AnimationHelper {
    companion object{
        val VERTICAL="translationY"
        val HORIZONTAL = "translationX"

        fun scaleClickAnimation(view:View,onClickCallback:()->Unit){
            view.setOnClickListener {
                val animX = ObjectAnimator.ofFloat(view,"ScaleX",0.95f)
                val animY = ObjectAnimator.ofFloat(view,"ScaleY",0.95f)
                animX.duration=100
                animY.duration=100
                val anim = AnimatorSet()
                anim.play(animX).with(animY)
                anim.addListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        val animX = ObjectAnimator.ofFloat(view,"ScaleX",1f)
                        val animY = ObjectAnimator.ofFloat(view,"ScaleY",1f)
                        animX.duration=100
                        animY.duration=100
                        val anim = AnimatorSet()
                        anim.play(animX).with(animY)
                        anim.addListener(object : AnimatorListenerAdapter(){
                            override fun onAnimationEnd(animation: Animator?) {
                                onClickCallback()
                            }
                        })
                        anim.start()
                    }
                })
                anim.start()
            }
        }


        fun moveView(view:View,moveDirection:String,fromValue:Float,toValue:Float):AnimatorSet{
            if (moveDirection == HORIZONTAL){
                view.translationX=fromValue
            } else {
                view.translationY=fromValue
            }
            view.alpha=0f
            val animatorTranslate = ObjectAnimator.ofFloat(view,moveDirection,toValue)
            animatorTranslate.duration = 500

            val animatorAlpha = ObjectAnimator.ofFloat(view,"alpha",1f)
            animatorAlpha.duration=500

            val anim = AnimatorSet()
            anim.play(animatorTranslate).with(animatorAlpha)
            return anim
        }
    }
}