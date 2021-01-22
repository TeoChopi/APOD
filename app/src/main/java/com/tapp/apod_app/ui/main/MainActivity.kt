package com.tapp.apod_app.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.tapp.apod_app.R
import com.tapp.apod_app.ui.detail.DetailActivity
import com.tapp.apod_app.ui.detail.DetailActivity.Companion.REQUEST_CODE
import com.tapp.apod_app.ui.detail.DetailActivity.Companion.SERVER_APOD
import com.tapp.apod_app.works.ApodWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        initializeWorker()

        supportFragmentManager.beginTransaction()
            .replace(R.id.content, MainFragment.newInstance())
            .commitNow()

        fab.setOnClickListener {

            Intent(this, DetailActivity::class.java).apply {
                putExtra("EXTRA_APOD", SERVER_APOD)
                startActivityForResult(this, REQUEST_CODE)
            }
        }
    }

    private fun initializeWorker() {

        val constraints = Constraints.Builder()
            //.setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val apodWorker = PeriodicWorkRequest.Builder(ApodWorker::class.java, 1, TimeUnit.DAYS)
            .addTag("ApodWork")
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(apodWorker)

        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(apodWorker.id).observe(this, Observer { apodWork ->
            apodWork?.let {
                if (apodWork.state == WorkInfo.State.SUCCEEDED) {

                    Log.w("TAG", "")
                }
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE)
            (supportFragmentManager.findFragmentById(R.id.content) as MainFragment).updateList()
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