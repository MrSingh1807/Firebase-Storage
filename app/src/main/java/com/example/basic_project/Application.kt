package com.example.basic_project

import android.app.Application
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class MyApplication : Application() {


    lateinit var firebaseInstance: FirebaseDatabase
    lateinit var myFirebaseReference: DatabaseReference

    lateinit var firebaseStorage: FirebaseStorage
    override fun onCreate() {
        super.onCreate()

        firebaseInstance = Firebase.database
        myFirebaseReference = firebaseInstance.reference

        firebaseStorage = FirebaseStorage.getInstance()

    }
}