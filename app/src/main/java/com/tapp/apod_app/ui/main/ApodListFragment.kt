package com.tapp.apod_app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tapp.apod_app.R
import com.tapp.apod_app.repository.model.Apod
import com.tapp.apod_app.ui.detail.DetailActivity
import com.tapp.apod_app.utils.ApiKey.EXTRA_APOD
import com.tapp.apod_app.utils.ApiKey.EXTRA_APOD_ID
import com.tapp.apod_app.utils.ApiKey.LOCAL_APOD
import com.tapp.apod_app.utils.ApiKey.OBJECT_APOD
import com.tapp.apod_app.utils.ApiKey.REQUEST_CODE
import com.tapp.apod_app.utils.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*

class ApodListFragment : Fragment(), ApodListItemDelegate, ApodListViewModelDelegate {

    private val mViewModel: ApodListFragmentViewModel by lazy {
        val factory = CustomViewModelFactory(requireActivity().application, this)
        ViewModelProvider(this, factory).get(ApodListFragmentViewModel::class.java)
    }

    private val apodAdapter: ApodListAdapter by lazy {
        val adapter = ApodListAdapter(this)
        adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
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