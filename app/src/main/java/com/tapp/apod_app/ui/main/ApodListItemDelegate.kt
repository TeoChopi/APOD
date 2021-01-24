package com.tapp.apod_app.ui.main

import com.tapp.apod_app.repository.model.Apod

interface ApodListItemDelegate {
    fun onItemClick(apod: Apod)
}