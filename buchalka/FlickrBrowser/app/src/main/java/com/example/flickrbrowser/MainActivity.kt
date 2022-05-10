package com.example.flickrbrowser

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.flickrbrowser.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), GetRawData.OnDownloadComplete,
    GetFlickrJsonData.OnDataAvailable {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val url = createUri(
            "https://www.flickr.com/services/feeds/photos_public.gne",
            searchCriteria = "android"
        )
        val getRawData = GetRawData(this)
        getRawData.execute(url)

        Log.d(TAG, "onCreate ends")
    }

    private fun createUri(
        baseUrl: String,
        searchCriteria: String,
        lang: String = "en-us",
        matchAll: Boolean = true
    ): String {
        Log.d(TAG, "createUri starts")
        return Uri.parse(baseUrl)
            .buildUpon()
            .appendQueryParameter("tags", searchCriteria)
            .appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY")
            .appendQueryParameter("lang", lang)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .build()
            .toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu called")
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(TAG, "onOptionsItemSelected called")
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        when (status) {
            DownloadStatus.OK -> {
                Log.d(TAG, "onDownloadComplete called")
                val getFlickrJsonData = GetFlickrJsonData(this)
                getFlickrJsonData.execute(data)
            }
            else -> Log.d(
                TAG,
                "onDownloadComplete failed with status $status, error message is $data"
            )
        }
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG, "onDataAvailable starts, data is $data")
        Log.d(TAG, "onDataAvailable ends, data is $data")
    }

    override fun onError(exception: Exception) {
        Log.e(TAG, "onError starts, error is ${exception.message}")
    }
}
