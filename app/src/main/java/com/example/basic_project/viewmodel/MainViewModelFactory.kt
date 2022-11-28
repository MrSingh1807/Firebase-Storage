package com.example.basic_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basic_project.MyApplication
import com.example.basic_project.repository.FirebaseRepository


class MainViewModelFactory(val repository: FirebaseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}