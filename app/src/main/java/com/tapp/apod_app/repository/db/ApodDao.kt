package com.tapp.apod_app.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tapp.apod_app.repository.model.Apod

@Dao
abstract class ApodDao {

    @Query("SELECT * FROM apod_table")
    abstract fun getAllApod(): LiveData<List<Apod>>

    @Query("SELECT * FROM apod_table")
    abstract fun getAll(): List<Apod>

    @Query("SELECT * FROM apod_table WHERE id = :apodId")
    abstract fun getApodId(apodId: String) : LiveData<Apod>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertApod(apod: Apod)

    @Delete
    abstract fun deleteApod(apod: Apod)
}