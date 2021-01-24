package com.tapp.apod_app.ui.detail

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.tapp.apod_app.repository.db.ApodRoomDatabase
import com.tapp.apod_app.repository.model.Apod
import com.tapp.apod_app.repository.services.ApodService
import com.tapp.apod_app.utils.ApiKey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(context: Application, private val owner: LifecycleOwner) : ViewModel() {

    var delegate: DetailViewModelDelegate? = null

    private val myApodRepository = ApodRoomDatabase.getInstance(context).apodDao()

    fun getApod() {
        ApodService().apodApi.getApod(ApiKey.API_KEY).enqueue(object : Callback<Apod> {
            override fun onResponse(call: Call<Apod>, response: Response<Apod>) {
                if (response.isSuccessful && response.body() != null) {
                    delegate?.onApodSuccess(response.body()!!)
                } else {
                    delegate?.onApodFailure(response.message())
                }
            }

            override fun onFailure(call: Call<Apod>, t: Throwable) {
                delegate?.onApodFailure(t.toString())
            }
        })
    }

    fun insertApod(apod: Apod) {
        myApodRepository.insertApod(apod)
        delegate?.insertApod(apod)
    }

    fun getLocalApodId(apodId: String) {
        return myApodRepository.getApodId(apodId).observe(owner, { apod ->
            if (apod != null) delegate?.getApod(apod)
        })
    }

    fun deleteApod(apod: Apod) {
        myApodRepository.deleteApod(apod)
        delegate?.deleteApod(apod)
    }
}