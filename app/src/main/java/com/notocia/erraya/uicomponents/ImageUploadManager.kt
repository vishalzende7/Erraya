package com.notocia.erraya.uicomponents

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.notocia.erraya.R
import com.notocia.erraya.models.Photo
import com.notocia.erraya.services.ErrayaApi
import com.notocia.erraya.services.onEnqueue
import com.notocia.erraya.utils.Profile
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File

class ImageUploadManager(c: Context, attrs: AttributeSet) : LinearLayout(c, attrs) {

    var photoList: ArrayList<Photo> = ArrayList()
    var photoChildList: ArrayList<View> = ArrayList()
    private var onPhotoAddClickListener: OnClickListener? = null
    private var count = 0

    init {
        orientation = VERTICAL
        inflate(context, R.layout.ui_component_upload_photo_widget, this)
    }


    //This class is responsible for uploading images to server one by one as we select
    //and maintain list of images added by user and image name received by server
    //and returning that image list back when a function called

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        prepareView(this)
    }

    fun setOnPhotoClickListener(l: OnClickListener) {
        onPhotoAddClickListener = l
    }

    private fun prepareView(v: View) {
        if (v is UploadPhoto) {
            v.id = photoList.size
            photoList.add(Photo(null, null, null))
            photoChildList.add(v)
            v.setOnClickListener(onPhotoAddClickListener)
            return
        } else {
            try {
                val count = (v as ViewGroup).childCount
                for (i in 0 until count) {
                    prepareView(v.getChildAt(i))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return
            }
        }
    }

    fun getUploadedImages(): List<Photo> {
        return photoList
    }

    fun setUploadImageUri(uri: Uri, pos: Int) {
        try {
            val file = File(uri.path!!)
            val body = RequestBody.create(MediaType.parse("image/*"), file)
            val part = MultipartBody.Part.createFormData("file", file.name, body)
            val apiToken = Profile.getInstance().api_token
            var name = file.name

            Log.i("MTTAG", "API TOKEN $apiToken")
            if (apiToken != null) {
                ErrayaApi.getInstance().addPhoto(part, apiToken)
                    .onEnqueue { call, response, throwable ->
                        if (throwable == null) {
                            if (response!!.isSuccessful) {
                                if (response.body()?.status == true) {
                                    photoList[pos].mUri = uri.toString()
                                    photoList[pos].server_id = response.body()!!.name
                                    (photoChildList[pos] as UploadPhoto).setImageUri(uri)
                                    count++

                                } else {

                                    Toast.makeText(context,
                                        response.body()?.message,
                                        Toast.LENGTH_SHORT).show()
                                }
                            } else {

                                val errString = response.errorBody()?.string()
                                if (!errString.isNullOrEmpty()) {
                                    val je = JSONObject(errString)
                                    val msg = je.getJSONObject("valData")
                                        .getString("message")
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    Log.i("MTTAG", "Response error ( $je )")
                                }
                            }
                        } else {
                            throw throwable
                        }
                    }
            } else {
                throw IllegalStateException("Api Token Should not null")
            }


        } catch (e: Exception) {
            Log.i("MTTAG", "error while uploading photo ${e.message}")
            e.printStackTrace()
        }
    }

    fun setNetworkImage(path: String, pos: Int) {
        Picasso.get()
            .load(path)
            .into((photoChildList[pos] as UploadPhoto).getImageView())
        (photoChildList[pos] as UploadPhoto).getImageView().visibility = VISIBLE
    }

    fun getCount() = count

}