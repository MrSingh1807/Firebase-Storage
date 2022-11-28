package com.example.basic_project.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.basic_project.repository.FirebaseRepository
import com.google.firebase.database.ChildEventListener

class MainViewModel (
    private val repository: FirebaseRepository
) : ViewModel() {

    val myRefrence = repository.myRefrence

    fun insertData(child: String, name: String, age: String, context: Context) {
        repository.insertData(child, name, age, context)
        }

//    fun childEventListener(): ChildEventListener{
//        return repository.childEventListener()
//    }

    fun updateData(child: String, name: String, age: String){
        repository.updateData(child, name, age)
    }

    fun deleteData(child: String){
        repository.deleteData(child)
    }

}