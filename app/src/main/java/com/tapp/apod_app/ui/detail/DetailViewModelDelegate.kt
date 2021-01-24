package com.tapp.apod_app.ui.detail

import com.tapp.apod_app.repository.model.Apod


interface DetailViewModelDelegate {
    fun onApodSuccess(apod: Apod)
    fun onApodFailure(error: String)
    fun getApod(apod: Apod)
    fun insertApod(apod: Apod)
    fun deleteApod(apod: Apod)
}