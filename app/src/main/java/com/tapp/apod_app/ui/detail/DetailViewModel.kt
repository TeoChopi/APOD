package com.tapp.apod_app.ui.detail

import android.app.Activity
import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tapp.apod_app.R
import com.tapp.apod_app.repository.db.ApodRoomDatabase
import com.tapp.apod_app.repository.network.ApodService
import com.tapp.apod_app.utils.ApiKey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val context: Application) : ViewModel() {

    fun getApod(cb: ApodService.CallbackResponse<Apod>) {
        ApodService().apodApi.getApod(ApiKey.API_KEY).enqueue(object : Callback<Apod> {
            override fun onResponse(call: Call<Apod>, response: Response<Apod>) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onResponse(response.body()!!)
                } else {
                    cb.onFailure(Throwable(response.message()), response)
                }
            }

            override fun onFailure(call: Call<Apod>, t: Throwable) {
                cb.onFailure(t)
            }
        })
    }


    fun insertApod(apod: Apod) {
        ApodRoomDatabase.getInstance(context).apodDao().insertApod(apod)
    }

    fun getLocalApodId(apodId: String) : LiveData<Apod>{
        return ApodRoomDatabase.getInstance(context).apodDao().getApodId(apodId)
    }

    fun deleteApod(apod: Apod) {
        ApodRoomDatabase.getInstance(context).apodDao().deleteApod(apod)
    }


    fun showApod(context: Activity, txtDescription: TextView, imageApodDetail: ImageView, apod: Apod) {

        txtDescription.text = apod.explanation

        Glide.with(context)
            .load(apod.url)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)

            )
            .into(imageApodDetail)
    }
}