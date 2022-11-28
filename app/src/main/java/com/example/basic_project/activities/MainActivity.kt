package com.example.basic_project.activities

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.basic_project.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {


    lateinit var myRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRef = FirebaseStorage.getInstance().reference.child("docs/")

        btn_addData.setOnClickListener {
            Toast.makeText(this, "Upload Started", Toast.LENGTH_SHORT).show()
            var inputStream: InputStream? = null
            try {
                inputStream = assets.open("india.jpeg")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            val uploadTAsk = inputStream?.let { it1 ->
                myRef.child("images/india.jpeg").putStream(
                    it1
                ) }

            val finalInputStream = inputStream

            uploadTAsk?.addOnSuccessListener {
                if (finalInputStream !=null) {
                    try {
                        finalInputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                    Toast.makeText(this, "Upload Success", Toast.LENGTH_SHORT).show()

            }
        }

    }


    fun readAsset(): Bitmap? {
        var inputStream: InputStream? = null
        try {
            inputStream = assets.open("indiaimage.jpg")
            val drawable = Drawable.createFromStream(inputStream, null) as BitmapDrawable

            return drawable.bitmap

        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (inputStream != null) {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

} 