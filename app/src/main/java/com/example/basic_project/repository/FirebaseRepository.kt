package com.example.basic_project.repository

import android.content.Context
import android.widget.Toast
import com.example.basic_project.modals.Child
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRepository {

    val myRefrence = Firebase.database.reference

    fun insertData(child: String, name: String, age: String, context: Context) {
//        key = myRefrence.push().key!!
        val person = Child()
//        myRefrence.child(child).child("Child").child("Name")
//            .setValue(edtTxt_insertData.text.toString())
//
        myRefrence.child(child).setValue(person).addOnSuccessListener {
            Toast.makeText(context, "Data successfully added", Toast.LENGTH_LONG).show()
        }
    }

//    fun childEventListener(): ChildEventListener {
//        return object : ChildEventListener {
//        }
//    }

    fun updateData(child: String, name: String, age: String){

        val person = Child()
        val map: MutableMap<String, Any> = mutableMapOf(child to person)

        myRefrence.updateChildren(map)
    }

    fun deleteData(child: String){
        myRefrence.child(child).removeValue()
    }
}