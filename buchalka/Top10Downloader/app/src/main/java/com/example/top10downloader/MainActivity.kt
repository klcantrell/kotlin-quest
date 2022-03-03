package com.example.top10downloader

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.top10downloader.databinding.ActivityMainBinding
import java.net.URL
import kotlin.properties.Delegates

class FeedEntry {
    var name: String = ""
    var artist: String = ""
    var releaseDate: String = ""
    var summary: String = ""
    var imageURL: String = ""

    override fun toString(): String {
        return """
            name = $name
            artist = $artist
            releaseDate = $releaseDate
            imageURL = $imageURL
        """.trimIndent()
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val TAG = "MainActivity"

    private var downloadData: DownloadData? = null

    private var feedUrl: String =
        "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
    private var feedLimit: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate() called")
        if (savedInstanceState != null) {
            val savedFeedUrl = savedInstanceState.getString(Constants.SAVED_FEED_URL, "")
            val savedFeedLimit = savedInstanceState.getInt(Constants.SAVED_FEED_LIMIT, 0)
            if (savedFeedUrl.isNotEmpty() && savedFeedLimit > 0) {
                feedUrl = savedFeedUrl
                feedLimit = savedFeedLimit
            }
        }
        downloadUrl(feedUrl.format(feedLimit))
        Log.d(TAG, "onCreate(): done")
    }

    private fun downloadUrl(feedUrl: String) {
        Log.d(TAG, "downloadUrl starting AsyncTask")
        downloadData = DownloadData(this, binding.xmlListView)
        downloadData?.execute(feedUrl)
        Log.d(TAG, "downloadUrl done")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.feeds_menu, menu)
        if (feedLimit == 10) {
            menu.findItem(R.id.mnu10).isChecked = true
        } else {
            menu.findItem(R.id.mnu25).isChecked = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var selectedFeedUrl = feedUrl
        val previousFeedLimit = feedLimit
        when (item.itemId) {
            R.id.mnuFree -> {
                selectedFeedUrl =
                    "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
            }
            R.id.mnuPaid -> {
                selectedFeedUrl =
                    "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml"
            }
            R.id.mnuSongs -> {
                selectedFeedUrl =
                    "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml"
            }
            R.id.mnu10, R.id.mnu25 ->
                if (!item.isChecked) {
                    item.isChecked = true
                    feedLimit = 35 - feedLimit
                    Log.d(
                        TAG,
                        "onOptionsItemSelected: ${item.title} setting feedLimit to $feedLimit"
                    )
                } else {
                    Log.d(
                        TAG,
                        "onOptionsItemSelected: ${item.title} setting feedLimit to $feedLimit"
                    )
                }
            R.id.mnuRefresh -> {
                downloadUrl(feedUrl.format(feedLimit))
            }
            else -> return super.onOptionsItemSelected(item)
        }

        if (selectedFeedUrl != feedUrl || previousFeedLimit != feedLimit) {
            feedUrl = selectedFeedUrl
            downloadUrl(feedUrl.format(feedLimit))
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        downloadData?.cancel(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.SAVED_FEED_URL, feedUrl)
        outState.putInt(Constants.SAVED_FEED_LIMIT, feedLimit)
    }

    companion object {
        private class DownloadData(context: Context, listView: ListView) :
            AsyncTask<String, Void, String>() {
            private val tag = "DownloadData"

            var propContext: Context by Delegates.notNull()
            var propListView: ListView by Delegates.notNull()

            init {
                propContext = context
                propListView = listView
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                Log.d(tag, "onPostExecute: parameter is $result")
                val parseApplications = ParseApplications()
                parseApplications.parse(result)

                val feedAdapter =
                    FeedAdapter(propContext, R.layout.list_record, parseApplications.applications)
                propListView.adapter = feedAdapter
            }

            override fun doInBackground(vararg params: String?): String {
                Log.d(tag, "doInBackground starts with: ${params[0]}")
                val rssFeed = downloadXML(params[0])
                if (rssFeed.isEmpty()) {
                    Log.d(tag, "doInBackground: Error downloading")
                }
                return rssFeed
            }

            private fun downloadXML(urlPath: String?): String {
                return URL(urlPath).readText()
            }
        }
    }
}

object Constants {
    const val SAVED_FEED_URL = "SAVED_FEED_URL"
    const val SAVED_FEED_LIMIT = "SAVED_FEED_LIMIT"
}
