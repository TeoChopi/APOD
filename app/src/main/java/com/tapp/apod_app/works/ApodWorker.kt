package com.tapp.apod_app.works

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tapp.apod_app.repository.db.ApodRoomDatabase
import com.tapp.apod_app.repository.model.Apod
import com.tapp.apod_app.repository.services.ApodService
import com.tapp.apod_app.utils.ApiKey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ApodWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {

        ApodService().apodApi.getApod(ApiKey.API_KEY).enqueue(object : Callback<Apod> {
            override fun onResponse(call: Call<Apod>, response: Response<Apod>) {
                if (response.isSuccessful && response.body() != null) {

                    val apodResponse = response.body()

                    try {
                        ApodRoomDatabase.getInstance(applicationContext).apodDao().getAll().first {
                            it.url == apodResponse!!.url
                        }
                    } catch (e: Exception) {
                        ApodRoomDatabase.getInstance(applicationContext).apodDao().insertApod(apodResponse!!)
                    }
                }
            }

            override fun onFailure(call: Call<Apod>, t: Throwable) {
                Log.w("TAG", t.localizedMessage!!)
            }
        })

        return Result.success()
    }
}