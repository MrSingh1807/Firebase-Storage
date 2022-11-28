package com.example.basic_project.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basic_project.R
import com.example.basic_project.modals.Child
import com.example.basic_project.utils.Constants.Companion.PUT_EXTRAS_KEY
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val uidValue = intent.extras?.getString(PUT_EXTRAS_KEY)!!

        val reference = Firebase.database.reference.child("Children").child(uidValue)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val child = snapshot.getValue(Child::class.java)
                if (child != null){
                    tv_output.text = child.name
            }
        }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}