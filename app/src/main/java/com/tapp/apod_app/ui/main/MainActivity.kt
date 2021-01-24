package com.tapp.apod_app.ui.main

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.tapp.apod_app.R
import com.tapp.apod_app.base.BaseApod
import com.tapp.apod_app.ui.apodList.ApodListFragment
import com.tapp.apod_app.ui.detail.DetailActivity
import com.tapp.apod_app.utils.ApiKey.EXTRA_APOD
import com.tapp.apod_app.utils.ApiKey.REQUEST_CODE
import com.tapp.apod_app.utils.ApiKey.SERVER_APOD
import com.tapp.apod_app.works.ApodWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : BaseApod.BaseActivity() {

    override fun getXmlLayout(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        setSupportActionBar(findViewById(R.id.toolbar))
        showApods()
        setWorker()
    }

    private fun showApods() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, ApodListFragment())
            .commitNow()
    }

    private fun setWorker() {

        val constraints = Constraints.Builder()
            //.setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val apodWorker = PeriodicWorkRequest.Builder(ApodWorker::class.java, 1, TimeUnit.DAYS)
            .addTag("ApodWork")
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(apodWorker)

        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(apodWorker.id).observe(this, { apodWork ->
            apodWork?.let {
                if (apodWork.state == WorkInfo.State.SUCCEEDED) {

                    Log.w("TAG", "")
                }
            }
        })
    }

    override fun initListeners() {
        fab.setOnClickListener {
            Intent(this, DetailActivity::class.java).apply {
                putExtra(EXTRA_APOD, SERVER_APOD)
                startActivityForResult(this, REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE)
            (supportFragmentManager.findFragmentById(R.id.content) as ApodListFragment).updateList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}