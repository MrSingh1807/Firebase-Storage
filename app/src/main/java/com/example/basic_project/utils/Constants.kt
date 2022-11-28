package com.example.basic_project.utils

import com.google.android.gms.tasks.Task
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Constants {

    companion object {
        const val PUT_EXTRAS_KEY = "String"
        const val TAG = "TAG"
    }

    fun removeChild(uid: String): Task<Void> {
        return Firebase.database.getReference("Children")
            .child(uid)
            .removeValue()
    }

}