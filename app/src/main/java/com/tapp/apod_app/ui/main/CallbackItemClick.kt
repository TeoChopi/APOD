package com.tapp.apod_app.ui.main

import com.tapp.apod_app.repository.model.ApodResponse

interface CallbackItemClick {
    fun onItemClick(apodResponse: ApodResponse)
}