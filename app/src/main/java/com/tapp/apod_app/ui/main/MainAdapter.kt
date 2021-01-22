package com.tapp.apod_app.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tapp.apod_app.R
import com.tapp.apod_app.repository.model.ApodResponse
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_list.view.*

class MainAdapter(private val context: Context, private val callbackItemClick: CallbackItemClick, private val apodList: List<ApodResponse>?) : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    class MainHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var view = v
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        apodList?.get(position).let { apod ->

            Glide.with(context)
                .load(apod?.url)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)

                )
                .into(holder.view.imageCardView)

            holder.view.carView.setOnClickListener {
                callbackItemClick.onItemClick(apod!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return  apodList!!.size
    }
}