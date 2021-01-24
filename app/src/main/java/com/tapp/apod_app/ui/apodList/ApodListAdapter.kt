package com.tapp.apod_app.ui.apodList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tapp.apod_app.R
import com.tapp.apod_app.repository.model.Apod
import kotlinx.android.synthetic.main.item_list.view.*

class ApodListAdapter(private val apodListItemDelegate: ApodListItemDelegate) : RecyclerView.Adapter<ApodListAdapter.ApodListHolder>() {

    private val apodList = mutableListOf<Apod>()

    class ApodListHolder(v: View) : RecyclerView.ViewHolder(v) {
        var apod: Apod? = null
            set(value) {
                field = value
                itemView.tag = field
                field?.let {
                    Glide.with(itemView)
                        .load(it.url)
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.ic_launcher_background)
                        )
                        .into(itemView.imageCardView)
                }
            }
    }

    fun setApods(apods: List<Apod>) {
        this.apodList.clear()
        this.apodList.addAll(apods)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodListHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ApodListHolder(view)
    }

    override fun onBindViewHolder(holder: ApodListHolder, position: Int) {
        apodList[position].let { apod ->
            holder.apod = apod
            holder.itemView.setOnClickListener {apodListItemDelegate.onItemClick(apod)}
        }
    }

    override fun getItemCount(): Int {
        return apodList.size
    }
}