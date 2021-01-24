package com.tapp.apod_app.ui.main

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.tapp.apod_app.repository.db.ApodRoomDatabase

class ApodListFragmentViewModel(context: Application, private val owner: LifecycleOwner) : ViewModel() {

    private val myApodRepository = ApodRoomDatabase.getInstance(context).apodDao()

    var delegate: ApodListViewModelDelegate? = null

    fun initialize() {
        getLocalAllApod()
    }

    private fun getLocalAllApod() {
        myApodRepository.getAllApod().observe(owner, {
            delegate?.onUpdateApods(it)
        })
    }
}