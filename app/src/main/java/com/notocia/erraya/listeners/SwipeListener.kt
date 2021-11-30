package com.notocia.erraya.listeners

import android.R.attr
import android.view.MotionEvent
import android.view.View
import com.notocia.erraya.listeners.SwipeListener.SwipeCallback

import android.view.ViewGroup
import android.R.attr.rotation
import android.R.attr.rotation
import android.animation.Animator
import android.view.animation.OvershootInterpolator

import android.view.ViewPropertyAnimator


class SwipeListener : View.OnTouchListener {

    private var ROTATION_DEGREES: Float = 15f
    var OPACITY_END: Float = 0.33f
    private var initialX: Float = 0f
    private var initialY: Float = 0f

    private var mActivePointerId: Int = 0
    private var initialXPress: Float = 0f
    private var initialYPress: Float = 0f
    private var parent: ViewGroup? = null
    private var parentWidth: Float = 0f
    private var paddingLeft: Int = 0

    private var card: View? = null
    var callback: SwipeCallback? = null
    private var deactivated = false
    private var rightView: View? = null
    private var leftView: View? = null

    constructor(
        card: View,
        callback: SwipeCallback,
        initialX: Float,
        initialY: Float,
        rotation: Float,
        opacityEnd: Float
    ) {
        this.card = card
        this.initialX = initialX
        this.initialY = initialY
        this.callback = callback
        parent = card.parent as ViewGroup
        parentWidth = parent!!.width.toFloat()
        ROTATION_DEGREES = attr.rotation.toFloat()
        OPACITY_END = opacityEnd
        paddingLeft = (card.parent as ViewGroup).paddingLeft
    }

    constructor(
        card: View,
        callback: SwipeCallback,
        initialX: Float,
        initialY: Float,
        rotation: Float,
        opacityEnd: Float,
        screenWidth: Int
    ) {
        this.card = card
        this.initialX = initialX
        this.initialY = initialY
        this.callback = callback
        parent = card.parent as ViewGroup
        parentWidth = screenWidth.toFloat()
        ROTATION_DEGREES = attr.rotation.toFloat()
        OPACITY_END = opacityEnd
        paddingLeft = (card.parent as ViewGroup).paddingLeft
    }

    private var click = true

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (deactivated) return false
        when (event!!.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                click = true
                //gesture has begun
                val x: Float
                val y: Float
                //cancel any current animations
                v!!.clearAnimation()
                mActivePointerId = event.getPointerId(0)
                x = event.x
                y = event.y
                if (event.findPointerIndex(mActivePointerId) === 0) {
                    callback!!.cardActionDown()
                }
                initialXPress = x
                initialYPress = y
            }
            MotionEvent.ACTION_MOVE -> {
                //gesture is in progress
                val pointerIndex = event.findPointerIndex(mActivePointerId)
                //Log.i("pointer index: " , Integer.toString(pointerIndex));
                if (pointerIndex < 0 || pointerIndex > 0) {
                    return false
                }
                val xMove = event.getX(pointerIndex)
                val yMove = event.getY(pointerIndex)

                //calculate distance moved
                val dx = xMove - initialXPress
                val dy = yMove - initialYPress

                //throw away the move in this case as it seems to be wrong
                //TODO: figure out why this is the case
                if (initialXPress.toInt() == 0 && initialYPress.toInt() == 0) {
                    //makes sure the pointer is valid
                    return false
                }
                //calc rotation here
                val posX = card!!.x + dx
                val posY = card!!.y + dy

                //in this circumstance consider the motion a click
                if (Math.abs(dx + dy) > 5) click = false
                card!!.x = posX
                card!!.y = posY

                //card.setRotation
                val distobjectX = posX - initialX
                val rotation = ROTATION_DEGREES * 2f * distobjectX / parentWidth
                card!!.rotation = rotation
                if (rightView != null && leftView != null) {
                    //set alpha of left and right image
                    val alpha = (posX - paddingLeft) / (parentWidth * OPACITY_END)
                    //float alpha = (((posX - paddingLeft) / parentWidth) * ALPHA_MAGNITUDE );
                    //Log.i("alpha: ", Float.toString(alpha));
                    rightView!!.alpha = alpha
                    leftView!!.alpha = -alpha
                }
            }
            MotionEvent.ACTION_UP -> {
                //gesture has finished
                //check to see if card has moved beyond the left or right bounds or reset
                //card position
                checkCardForEvent()
                if (event.findPointerIndex(mActivePointerId) === 0) {
                    callback!!.cardActionUp()
                }
                //check if this is a click event and then perform a click
                //this is a workaround, android doesn't play well with multiple listeners
                if (click) v!!.performClick()
            }
            else -> return false
        }
        return true
    }

    fun checkCardForEvent() {
        if (cardBeyondLeftBorder()) {
            animateOffScreenLeft(160)
                ?.setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        callback!!.cardOffScreen()
                    }

                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })
            callback!!.cardSwipedLeft()
            deactivated = true
        } else if (cardBeyondRightBorder()) {
            animateOffScreenRight(160)
                ?.setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        callback!!.cardOffScreen()
                    }

                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })
            callback!!.cardSwipedRight()
            deactivated = true
        } else {
            resetCardPosition()
        }
    }

    private fun cardBeyondLeftBorder(): Boolean {
        //check if cards middle is beyond the left quarter of the screen
        return card!!.x + card!!.width / 2 < parentWidth / 4f
    }

    private fun cardBeyondRightBorder(): Boolean {
        //check if card middle is beyond the right quarter of the screen
        return card!!.x + card!!.width / 2 > parentWidth / 4f * 3
    }

    private fun resetCardPosition(): ViewPropertyAnimator? {
        if (rightView != null) rightView!!.alpha = 0f
        if (leftView != null) leftView!!.alpha = 0f
        return card!!.animate()
            .setDuration(200)
            .setInterpolator(OvershootInterpolator(1.5f))
            .x(initialX)
            .y(initialY)
            .rotation(0f)
    }

    fun animateOffScreenLeft(duration: Int): ViewPropertyAnimator? {
        return card!!.animate()
            .setDuration(duration.toLong())
            .x(-parentWidth)
            .y(0f)
            .rotation(-30f)
    }

    fun animateOffScreenRight(duration: Int): ViewPropertyAnimator? {
        return card!!.animate()
            .setDuration(duration.toLong())
            .x(parentWidth * 2)
            .y(0f)
            .rotation(30f)
    }

    fun setRightView(image: View?) {
        rightView = image
    }

    fun setLeftView(image: View?) {
        leftView = image
    }

    interface SwipeCallback {
        fun cardSwipedLeft()
        fun cardSwipedRight()
        fun cardOffScreen()
        fun cardActionDown()
        fun cardActionUp()
    }
}