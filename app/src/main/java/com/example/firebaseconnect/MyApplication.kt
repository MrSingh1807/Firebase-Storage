package com.example.firebaseconnect

import android.app.Application
import com.google.firebase.storage.FirebaseStorage

class MyApplication: Application() {

    lateinit var fireBase: FirebaseStorage

    override fun onCreate() {

        super.onCreate()
        fireBase = FirebaseStorage.getInstance()
    }
}