package com.example.firebaseconnect

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_second.*
import java.io.File

// **** Download any File from Firebase Storage ******
class Second : AppCompatActivity() {

    lateinit var fireBaseReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        fireBaseReference = FirebaseStorage.getInstance().getReference("docs/")
        getPermissions()

        btn_downloadData.setOnClickListener {
//            downloadImageWithBytes()
            downloadImageWithFiles()
        }
    }

    //    verify all permissions
    private fun getPermissions() {
        val extReadPermission = Manifest.permission.READ_EXTERNAL_STORAGE
        val extWritePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE

        if (ContextCompat.checkSelfPermission(
                this,
                extReadPermission
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                extWritePermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(extReadPermission, extWritePermission), 101)
            }
        }
    }

    //    download file with getBytes()
    /* private fun downloadImageWithBytes() {
        val output = File(Environment.getExternalStorageDirectory(), "img.jpeg")
        val ONE_MB: Long = 1024 * 1024

        fireBaseReference.child("images/image:1000000495.jpeg").getBytes(ONE_MB)
            .addOnSuccessListener { bytes ->

                Toast.makeText(this, "File Downloaded", Toast.LENGTH_SHORT).show()
                imgVw_download.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))


                try {
                    val fileOutputStream = FileOutputStream(output)
                    fileOutputStream.write(bytes)
                    fileOutputStream.close()

                    Log.d(TAG, "File Successfully saved in ${output.name}")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "File download Failed $it")
                Toast.makeText(this, "File Download Failed -- $it", Toast.LENGTH_SHORT).show()

            }
    }  */

    //    download file with getFile()
    private fun downloadImageWithFiles() {
        val outPut = File(Environment.getExternalStorageDirectory(), "img.jpeg")

        fireBaseReference.child("images/india.jpeg").getFile(outPut)
            .addOnSuccessListener {

                Toast.makeText(this, "File Downloaded in $outPut", Toast.LENGTH_SHORT).show()
                imgVw_download.setImageURI(Uri.fromFile(outPut))
            }
            .addOnFailureListener {
                Toast.makeText(this, "File Download failed", Toast.LENGTH_SHORT).show()
            }
    }
}