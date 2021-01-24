package com.tapp.apod_app.ui.detail

import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tapp.apod_app.R
import com.tapp.apod_app.base.BaseApod
import com.tapp.apod_app.repository.model.Apod
import com.tapp.apod_app.repository.network.ApodService
import com.tapp.apod_app.utils.ApiKey.LOCAL_APOD
import com.tapp.apod_app.utils.CustomViewModelFactory
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Response

class DetailActivity : BaseApod.BaseActivity() {

    companion object {
        const val TAG = "DetailActivity"
    }

    private var mApod: Apod? = null
    private var localApod = false

    private val mViewModel: DetailViewModel by lazy {
        val factory = CustomViewModelFactory(application, this)
        ViewModelProvider(this, factory).get(DetailViewModel::class.java)
    }

    override fun getXmlLayout(): Int {
        return R.layout.activity_detail
    }

    override fun initValues() {
        setSupportActionBar(findViewById(R.id.toolbar))
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        intent?.let {
            if (it.getStringExtra("EXTRA_APOD") == LOCAL_APOD) {

                //mApodResponse = intent.extras!!.getSerializable(OBJECT_APOD) as ApodResponse?
                //mViewModel.showApod(this@DetailActivity, txtDescription, imageApodDetail, mApodResponse!!)

                localApod = true
                btnApodDetail.setImageResource(android.R.drawable.ic_menu_delete)
                mViewModel.getLocalApodId(it.getStringExtra("EXTRA_APOD_ID")!!)
                    .observe(this, Observer { apod ->

                        if (apod != null) {
                            mApod = apod
                            mViewModel.showApod(this@DetailActivity, txtDescription, imageApodDetail, apod)
                        }
                    })

            } else {
                localApod = false
                btnApodDetail.setImageResource(android.R.drawable.ic_menu_save)
                getServerApod()
            }
        } ?: getServerApod()
    }

    override fun initListeners() {
        btnApodDetail.setOnClickListener {

            if (localApod) {
                mViewModel.deleteApod(mApod!!)
            } else {

                Completable.fromAction {
                    mViewModel.insertApod(mApod!!)
                }.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : CompletableObserver {
                        override fun onComplete() {
                            Log.w(TAG, "Insert OK")
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.w(TAG, "Subscribe OK")
                        }

                        override fun onError(e: Throwable) {
                            Log.w(TAG, e.printStackTrace().toString())
                        }
                    })
            }

            finish()
        }
    }

    private fun getServerApod() {
        mViewModel.getApod(object : ApodService.CallbackResponse<Apod> {
            override fun onResponse(response: Apod) {
                mApod = response
                mViewModel.showApod(this@DetailActivity, txtDescription, imageApodDetail, mApod!!)
            }

            override fun onFailure(t: Throwable, res: Response<*>?) {
                txtDescription.text = res.toString()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Log.w(TAG, "")
    }
}