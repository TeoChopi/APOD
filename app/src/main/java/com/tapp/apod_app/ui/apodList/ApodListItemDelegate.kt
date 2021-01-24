package com.tapp.apod_app.ui.apodList

import com.tapp.apod_app.repository.model.Apod

interface ApodListItemDelegate {
    fun onItemClick(apod: Apod)
}