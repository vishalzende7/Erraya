package com.notocia.erraya.uicomponents

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.notocia.erraya.R
import com.notocia.erraya.services.ErrayaApi
import com.notocia.erraya.services.onEnqueue
import com.notocia.erraya.utils.Profile
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File

class UploadPhoto : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, int: Int) : super(context, attrs, int)

    private val imageButton: ImageView
    private val profileImage: ImageView

    private var listener: OnClickListener? = null
    private var hasImageUri: Boolean = false
    private var mUri: Uri? = null
        set(value) {
            if (value != field) {
                field = value
                profileImage.setImageURI(mUri)
                profileImage.visibility = View.VISIBLE
                imageButton.visibility = View.GONE
            }
        }

    private val mListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
    }

    private val mDetector = GestureDetector(context, mListener)

    private val mTouchListener = object : View.OnTouchListener {
        override fun onTouch(p0: View?, event: MotionEvent?): Boolean {
            mDetector.onTouchEvent(event).let { result ->
                return if (!result) {
                    if (event?.action == MotionEvent.ACTION_UP) {
                        performClick()
                        true
                    } else false
                } else
                    true
            }
        }
    }

    init {
        inflate(context, R.layout.ui_component_upload_photos, this)
        profileImage = findViewById(R.id.profileImage)
        imageButton = findViewById(R.id.buttonAdd)

        if (hasImageUri) {
            profileImage.visibility = View.VISIBLE
            profileImage.setImageURI(mUri!!)
        } else
            profileImage.visibility = GONE

        setOnTouchListener(mTouchListener)
    }

    override fun performClick(): Boolean {
        super.performClick()
        listener?.onClick(this)
        return true
    }

    fun setImageUri(uri: Uri){
        mUri = uri
    }

    fun getImageView() = profileImage

    fun getImageUri(): Uri? {
        return mUri
    }


    override fun setOnClickListener(l: OnClickListener?) {
        this.listener = l
    }
}