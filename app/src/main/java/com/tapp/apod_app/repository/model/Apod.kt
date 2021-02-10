package com.tapp.apod_app.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity(tableName = Apod.TABLE_NAME)
data class Apod(

    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("media_type")
    val mediaType: String? = null,

    @field:SerializedName("url")
    val hdurl: String? = null,

    @field:SerializedName("service_version")
    val serviceVersion: String? = null,

    @field:SerializedName("explanation")
    val explanation: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null

) : Serializable {

    companion object {
        const val TABLE_NAME = "apod_table"
    }
}
