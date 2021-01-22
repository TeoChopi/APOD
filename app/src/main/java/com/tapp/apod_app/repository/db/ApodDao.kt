package com.tapp.apod_app.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tapp.apod_app.repository.model.ApodResponse
import io.reactivex.Completable

@Dao
abstract class ApodDao {

    @Query("SELECT * FROM apod_table")
    abstract fun getAllApod(): LiveData<List<ApodResponse>>

    @Query("SELECT * FROM apod_table")
    abstract fun getAll(): List<ApodResponse>

    @Query("SELECT * FROM apod_table WHERE id = :apodId")
    abstract fun getApodId(apodId: String) : LiveData<ApodResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertApod(apodResponse: ApodResponse)

    @Delete
    abstract fun deleteApod(apodResponse: ApodResponse)
}