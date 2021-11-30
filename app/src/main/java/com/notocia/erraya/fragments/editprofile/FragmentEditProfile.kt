package com.notocia.erraya.fragments.editprofile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.notocia.erraya.R
import com.notocia.erraya.models.BasicInfo
import com.notocia.erraya.services.ErrayaApi
import com.notocia.erraya.services.onEnqueue
import com.notocia.erraya.uicomponents.ImageUploadManager
import com.notocia.erraya.uicomponents.InfoWidget
import com.notocia.erraya.utils.InfoWidgetUtils
import com.notocia.erraya.utils.Profile
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FragmentEditProfile : Fragment() {

    private val PHOTOREQ = 1
    private val CAMERA_PERMISSION = 2
    private val STORAGE_PERMISSION = 3
    private val PICK_IMAGE = 4

    private lateinit var basicInfoWidget: InfoWidget
    private lateinit var interestInfoWidget: InfoWidget
    private lateinit var aboutInfoWidget: InfoWidget
    private lateinit var uploadManager: ImageUploadManager
    private var currentWorkingId: Int = -1
    private var currentFile: String = ""
    private var picUrls: Array<String>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val v = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        basicInfoWidget = v.findViewById(R.id.basicInfoWidget)
        interestInfoWidget = v.findViewById(R.id.interestInfoWidget)
        aboutInfoWidget = v.findViewById(R.id.aboutInfoWidget)
        uploadManager = v.findViewById(R.id.imageUploadManager)
        uploadManager.setOnPhotoClickListener(onPhotoAddClickListener)
        prepInfo()
        addImages()
        return v
    }

    private fun addImages() {
        val api_token = Profile.getInstance().api_token
        if (api_token != null) {
            ErrayaApi.getInstance().getProfile(api_token).onEnqueue { call, response, throwable ->
                if (throwable == null) {
                    if (response!!.isSuccessful) {
                        val r = response.body()
                        if (r == null) {
                            Toast.makeText(context,
                                "Response body empty server error",
                                Toast.LENGTH_SHORT).show()
                            return@onEnqueue
                        }
                        picUrls = r.profileData?.pic_url
                        if (picUrls != null) {
                            val base_url =
                                "http://apierrya.bigint.in/static/profile_images/compressed/"
                            for ((count, i) in picUrls!!.withIndex()) {
                                if (i.isNotEmpty()) {
                                    uploadManager.setNetworkImage(base_url + i, count)
                                    Log.i("MTTAG", "Image link ${base_url + i}")
                                }
                            }
                        }

                    } else {
                        //Log out user and re-login
                    }
                } else {
                    throwable.printStackTrace()
                }
            }
        } else {
            throw IllegalStateException("API token can not be null")
        }
    }


    private val onPhotoAddClickListener = View.OnClickListener {
        Log.i("MTTAG", "Id is ${it.id}")
        currentWorkingId = it.id
        val options: Array<CharSequence> =
            arrayOf("Take Photo", "Choose form Gallery", "Cancel")
        val dialogBuilder = AlertDialog.Builder(context).apply {
            setTitle("Select Photo")
            setItems(options) { dialog, which ->
                when (which) {
                    0 -> {
                        //Open Camera
                        if (ContextCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            openCamera()
                        } else {
                            requestPermissions(
                                arrayOf(
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ),
                                CAMERA_PERMISSION
                            )
                        }
                    }
                    1 -> {
                        //Image from gallery
                        if (ContextCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            openGallery()
                        } else {
                            requestPermissions(
                                arrayOf(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ),
                                CAMERA_PERMISSION
                            )
                        }
                    }
                }
            }
        }
        dialogBuilder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("MTTAG", "On Activity Result $requestCode")
        if (requestCode == PHOTOREQ && resultCode == Activity.RESULT_OK) {
            //Camera Image Req.
            val fileUri: Uri = FileProvider.getUriForFile(
                requireContext(),
                requireContext().applicationContext.packageName + ".provider",
                File(currentFile)
            )
            CropImage.activity(fileUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(requireContext(), this)

        } else if (requestCode == PICK_IMAGE) {
            if (data == null)
                return
            val fileUri = data.data
            CropImage.activity(fileUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(requireContext(), this)

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val mResult = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val fileUri = mResult.uri
                uploadManager.setUploadImageUri(fileUri, currentWorkingId)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val e = mResult.error
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun prepInfo() {
        val bInfoList = ArrayList<BasicInfo>()

        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(0), "5'11 feet", 0))
        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(1), "Active", 1))
        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(2), "Postgraduate Degree", 2))
        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(3), "Sagittarius", 3))
        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(4), "Hinduism", 4))
        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(5), "Socially", 5))
        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(6), "Regularly", 6))
        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(7), "Banglore", 7))

        basicInfoWidget.addAllWidget(bInfoList)

        val iInfoList = ArrayList<BasicInfo>()

        iInfoList.add(BasicInfo(InfoWidgetUtils.getIIIcon(0), "Swimming", 0))
        iInfoList.add(BasicInfo(InfoWidgetUtils.getIIIcon(1), "Reading", 1))
        iInfoList.add(BasicInfo(InfoWidgetUtils.getIIIcon(2), "Cricket", 2))


        interestInfoWidget.addAllWidgetWithoutIcon(iInfoList)

        aboutInfoWidget.addPlainText(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ac sit mauris risus auctor urna, ut sodales molestie maecenas. Quis erat elementum scelerisque mauris. \n" +
                    "Quisque rutrum proin sed in mi dui lectus vulputate id. Nulla adipiscing vel convallis cras a. Quisque faucibus nec porta sit."
        )
    }

    private fun openGallery() {
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, PICK_IMAGE)
    }

    private fun openCamera() {
        try {
            val tmpFile = createImageFile()
            val tmpUri = FileProvider.getUriForFile(
                requireContext(),
                requireContext().applicationContext?.packageName + ".provider",
                tmpFile
            )
            Log.i("MTTAG", "Capture image Temp Uri $tmpUri")
            val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, tmpUri)
            startActivityForResult(captureIntent, PHOTOREQ)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "JPEG_$timeStamp"
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(fileName, ".jpg", storageDir)
        currentFile = image.absolutePath
        return image
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            CAMERA_PERMISSION -> {
                openCamera()
            }
            STORAGE_PERMISSION -> {
                //Choose from gallery
            }
        }
    }
}