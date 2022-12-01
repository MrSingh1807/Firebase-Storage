package com.example.firebaseconnect


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseconnect.Constants.TAG
import com.example.firebaseconnect.viewmodels.MainViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var fireBaseRefer: StorageReference
    lateinit var mainViewModel: MainViewModel
    private val STORAGE_PERMISSION_CODE = 100
    private var Granted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        fireBaseRefer = FirebaseStorage.getInstance().getReference("docs/")

// ************  Buttons  *************
        btn_readData.setOnClickListener {
            val intent = Intent(this, Second::class.java)
            startActivity(intent)
        }

        btn_run_code.setOnClickListener {
//        give a popUp to Grant the permission to user
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Granted) {    //  if permission Granted --> false,  then return to OnClick
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            STORAGE_PERMISSION_CODE
                        )
                        return@setOnClickListener
                    }
                }
            }

//       create an Intent to pick date (file) from local storage of device
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            imagePickFromGallery.launch(intent)


//          *****************  upload file via :putStream() method  ********************
            /*  var inputStream: InputStream? = null
              try {
                  inputStream = assets.open("india.jpeg")
              } catch (e: FileNotFoundException) {
                  e.printStackTrace()
              }
              val uploadTask =
                  inputStream?.let { it1 -> fireBaseRefer.child("images/india.jpeg").putStream(it1) }
              val finalInput = inputStream
              uploadTask?.addOnSuccessListener {
                  if (finalInput != null) {
                      try {
                          finalInput.close()
                      } catch (e: IOException) {
                          e.printStackTrace()
                      }
                  }

                  Toast.makeText(this, "Upload Success", Toast.LENGTH_SHORT).show()
                  Log.d("tag", "Upload Success")
              } */
        }
    }

    //    function: onActivityResult()  is deprecated
//    newer form of the code --->
    private var imagePickFromGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {

                val imageURI = result.data?.data
                val uploadTask =
                    fireBaseRefer.child("images/${imageURI?.lastPathSegment}").putFile(imageURI!!)

                uploadTask.addOnProgressListener { takeSnap ->
                    // calculate Transferred Bytes into persantage form
                    val progress: Double =
                        ((takeSnap.bytesTransferred * 100) / takeSnap.totalByteCount).toDouble()
                    progress_bar.progress = progress.toInt()

                    tv_progress.text = "$progress  % "
                    Log.d(TAG, "Upload Progress $progress % uploaded")
                }

                uploadTask.addOnSuccessListener { snap ->
                    Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show()
                    tv_progress.append("File Uploaded")

                    // ie: code here, If you want to show image in a imageView
                }
            }
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                Granted = true
            } else {
                Toast.makeText(
                    this,
                    "Please Provide the Permission to Read Data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}

