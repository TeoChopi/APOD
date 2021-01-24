package com.tapp.apod_app.ui.detail

import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tapp.apod_app.R
import com.tapp.apod_app.base.BaseApod
import com.tapp.apod_app.repository.model.Apod
import com.tapp.apod_app.utils.ApiKey.EXTRA_APOD
import com.tapp.apod_app.utils.ApiKey.EXTRA_APOD_ID
import com.tapp.apod_app.utils.ApiKey.LOCAL_APOD
import com.tapp.apod_app.utils.CustomViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseApod.BaseActivity(), DetailViewModelDelegate {

    private var mApod: Apod? = null
    private var localApod = false

    private val mViewModel: DetailViewModel by lazy {
        val factory = CustomViewModelFactory(application, this)
        ViewModelProvider(this, factory).get(DetailViewModel::class.java)
    }

    override fun getXmlLayout(): Int {
        return R.layout.activity_detail
    }

    override fun init() {
        setSupportActionBar(findViewById(R.id.toolbar))

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mViewModel.delegate = this

        intent?.let {
            if (it.getStringExtra(EXTRA_APOD) == LOCAL_APOD) {
                localApod = true
                btnApodDetail.setImageResource(android.R.drawable.ic_menu_delete)
                mViewModel.getLocalApodId(it.getStringExtra(EXTRA_APOD_ID)!!)
            } else {
                localApod = false
                btnApodDetail.setImageResource(android.R.drawable.ic_menu_save)
                mViewModel.getApod()
            }
        } ?: mViewModel.getApod()
    }

    override fun initListeners() {
        btnApodDetail.setOnClickListener {
            if (localApod) mViewModel.deleteApod(mApod!!)
            else mViewModel.insertApod(mApod!!)
            finish()
        }
    }

    override fun onApodSuccess(apod: Apod) {
        showApod(apod)
    }

    override fun onApodFailure(error: String) {
        txtDescription.text = error
    }

    override fun getApod(apod: Apod) {
        showApod(apod)
    }

    override fun insertApod(apod: Apod) {

    }

    override fun deleteApod(apod: Apod) {

    }

    private fun showApod(apod: Apod) {
        mApod = apod
        txtDescription.text = apod.explanation
        Glide.with(this)
            .load(apod.url)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)

            )
            .into(imageApodDetail)
    }
}