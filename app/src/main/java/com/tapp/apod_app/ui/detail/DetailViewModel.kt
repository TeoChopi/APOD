package com.tapp.apod_app.ui.detail

import android.app.Activity
import android.app.Application
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tapp.apod_app.R
import com.tapp.apod_app.repository.db.ApodRoomDatabase
import com.tapp.apod_app.repository.model.ApodResponse
import com.tapp.apod_app.repository.network.ApodService
import com.tapp.apod_app.utils.ApiKey
import io.reactivex.Completable
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val context: Application) : ViewModel() {

    fun getApod(cb: ApodService.CallbackResponse<ApodResponse>) {
        ApodService().apodApi.getApod(ApiKey.API_KEY).enqueue(object : Callback<ApodResponse> {
            override fun onResponse(call: Call<ApodResponse>, response: Response<ApodResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onResponse(response.body()!!)
                } else {
                    cb.onFailure(Throwable(response.message()), response)
                }
            }

            override fun onFailure(call: Call<ApodResponse>, t: Throwable) {
                cb.onFailure(t)
            }
        })
    }


    fun insertApod(apodResponse: ApodResponse) {
        ApodRoomDatabase.getInstance(context).apodDao().insertApod(apodResponse)
    }

    fun getLocalApodId(apodId: String) : LiveData<ApodResponse>{
        return ApodRoomDatabase.getInstance(context).apodDao().getApodId(apodId)
    }

    fun deleteApod(apodResponse: ApodResponse) {
        ApodRoomDatabase.getInstance(context).apodDao().deleteApod(apodResponse)
    }


    fun showApod(context: Activity, txtDescription: TextView, imageApodDetail: ImageView, apodResponse: ApodResponse) {

        txtDescription.text = apodResponse.explanation

        Glide.with(context)
            .load(apodResponse.url)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)

            )
            .into(imageApodDetail)
    }
}