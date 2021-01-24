package com.tapp.apod_app.utils

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tapp.apod_app.ui.main.ApodListFragmentViewModel
import com.tapp.apod_app.ui.detail.DetailViewModel

class CustomViewModelFactory(private val application: Application, private val owner: LifecycleOwner) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(ApodListFragmentViewModel::class.java) -> ApodListFragmentViewModel(application, owner)
                isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(application, owner)
                else -> throw IllegalArgumentException("Unknown ViewModel")
            }
        } as T
    }
}