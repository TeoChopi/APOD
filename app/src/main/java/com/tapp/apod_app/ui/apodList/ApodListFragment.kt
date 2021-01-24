package com.tapp.apod_app.ui.apodList

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tapp.apod_app.R
import com.tapp.apod_app.base.BaseApod
import com.tapp.apod_app.repository.model.Apod
import com.tapp.apod_app.ui.detail.DetailActivity
import com.tapp.apod_app.utils.ApiKey.EXTRA_APOD
import com.tapp.apod_app.utils.ApiKey.EXTRA_APOD_ID
import com.tapp.apod_app.utils.ApiKey.LOCAL_APOD
import com.tapp.apod_app.utils.ApiKey.OBJECT_APOD
import com.tapp.apod_app.utils.ApiKey.REQUEST_CODE
import com.tapp.apod_app.utils.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*

class ApodListFragment : BaseApod.BaseFragment(), ApodListItemDelegate, ApodListViewModelDelegate {

    private val mViewModel: ApodListFragmentViewModel by lazy {
        val factory = CustomViewModelFactory(requireActivity().application, this)
        ViewModelProvider(this, factory).get(ApodListFragmentViewModel::class.java)
    }

    private val apodAdapter: ApodListAdapter by lazy {
        val adapter = ApodListAdapter(this)
        adapter
    }

    override fun getXmlLayout(): Int {
        return R.layout.fragment_main
    }

    override fun init() {
        recyclerViewMainList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerViewMainList.adapter = apodAdapter
        mViewModel.delegate = this
        mViewModel.initialize()
    }

    override fun onUpdateApods(apods: List<Apod>) {
        apodAdapter.setApods(apods)
    }

    override fun onItemClick(apod: Apod) {
        activity?.let { fragment ->
            Intent(fragment, DetailActivity::class.java).apply {

                arguments = Bundle().apply {
                    putSerializable(OBJECT_APOD, apod)
                }

                arguments?.let { args ->
                    putExtras(args)
                }

                putExtra(EXTRA_APOD, LOCAL_APOD)
                putExtra(EXTRA_APOD_ID, apod.id)
                fragment.startActivityForResult(this, REQUEST_CODE)
            }
        }
    }

    fun updateList() {
        apodAdapter.notifyDataSetChanged()
    }
}