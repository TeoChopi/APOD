package com.tapp.apod_app.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tapp.apod_app.repository.db.ApodRoomDatabase
import com.tapp.apod_app.repository.model.ApodResponse

class MainFragmentViewModel(private val context: Application) : ViewModel() {

    fun gelLocalAllApod() : LiveData<List<ApodResponse>> {
         return ApodRoomDatabase.getInstance(context).apodDao().getAllApod()
    }
}