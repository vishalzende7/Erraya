package com.notocia.erraya.fragments.profiles

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
import androidx.navigation.fragment.findNavController
import club.cred.synth.views.SynthButton
import com.notocia.erraya.R
import com.notocia.erraya.uicomponents.ImageUploadManager
import com.notocia.erraya.utils.Profile
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class UploadPhotos : Fragment() {

    private val PHOTOREQ = 1
    private val CAMERA_PERMISSION = 2
    private val STORAGE_PERMISSION = 3
    private val PICK_IMAGE = 4

    private lateinit var nextButton: SynthButton
    private lateinit var uploadManager: ImageUploadManager
    private var currentWorkingId: Int = -1
    private var currentFile: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upload_photos, container, false)
        nextButton = view.findViewById(R.id.buttonContinue)
        uploadManager = view.findViewById(R.id.imageUploadManager)

        uploadManager.setOnPhotoClickListener(onPhotoAddClickListener)
        nextButton.setOnClickListener {
            if(uploadManager.getCount() > 1) {
                Profile.getInstance().photos = uploadManager.photoList
                findNavController().navigate(R.id.action_uploadPhotos_to_interestFragment)
            }
            else
                Toast.makeText(context,"Please upload at-least 2 Images",Toast.LENGTH_SHORT).show()
        }
        return view
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
        grantResults: IntArray
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