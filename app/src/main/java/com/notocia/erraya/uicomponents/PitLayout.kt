package com.notocia.erraya.uicomponents

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import club.cred.synth.SynthUtils
import club.cred.synth.appearances.PitViewAppearance
import club.cred.synth.appearances.PitViewAppearance.Companion.getPitViewAppearance
import club.cred.synth.drawables.PitDrawable
import club.cred.synth.internals.PitHelper
import com.notocia.erraya.R

class PitLayout(c: Context, attrs: AttributeSet) : ConstraintLayout(c, attrs) {

    var pitColor: Int by dynamicAttr(SynthUtils.defaultBaseColor) {

    }

    var pitViewAppearance: PitViewAppearance by dynamicAttr(
        PitViewAppearance(SynthUtils.defaultBaseColor), this::updateBackground
    )
    var cornerRadiusArg: Float by dynamicAttr(20f.dp, this::updateBackground)
    var pitClipType: Int by dynamicAttr(PitDrawable.NO_CLIP, this::updateBackground)
    var drawBackground by dynamicAttr(true, this::updateBackground)
    var drawShadows by dynamicAttr(true, this::updateBackground)
    var isSoft by dynamicAttr(true, this::updateBackground)
    var isCard by dynamicAttr(true, this::updateBackground)
    var pressedDepth: Float by dynamicAttr(PitHelper.DEFAULT_PRESSED_DEPTH, this::updateBackground)

    init {
        withAttrs(attrs, R.styleable.PitLayout){
            pitColor = getInt(R.styleable.PitLayout_pitColor,pitColor)
            pitViewAppearance = getPitViewAppearance(context,pitColor)
            pitClipType = getInt(R.styleable.PitLayout_pitClipType,pitClipType)
            cornerRadiusArg = getDimension(R.styleable.PitLayout_neuCornerRadius,cornerRadiusArg)
            drawBackground = getBoolean(R.styleable.PitLayout_drawBackground, drawBackground)
            drawShadows = getBoolean(R.styleable.PitLayout_drawShadows,drawShadows)
            isSoft = getBoolean(R.styleable.PitLayout_isSoft, isSoft)
            isCard = getBoolean(R.styleable.PitLayout_isCard,isCard)
            pressedDepth = getDimension(R.styleable.PitLayout_pitDepth,pressedDepth)
        }

        updateBackground()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun <T> updateBackground(t: T) {
        updateBackground()
    }

    private fun updateBackground() {
        background = PitDrawable(
            pitViewAppearance = pitViewAppearance,
            cornerRadiusArg = cornerRadiusArg,
            drawBackground = drawBackground,
            drawShadows = drawShadows,
            isSoft = isSoft,
            isCard = isCard,
            pressedDepth = pressedDepth,
            pitClipType = pitClipType
        )
    }
}