package com.example.top10downloader

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate called")
        val downloadData = DownloadData()
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
        Log.d(TAG, "onCreate done")
    }

    companion object {
        private class DownloadData : AsyncTask<String, Void, String>() {
            private val TAG = "DownloadData"

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Log.d(TAG, "onPostExecute: parameter is $result")
            }

            override fun doInBackground(vararg params: String?): String {
                Log.d(TAG, "doInBackground starts with: ${params[0]}")
                val rssFeed = downloadXML(params[0])
                if (rssFeed.isEmpty()) {
                    Log.d(TAG, "doInBackground: Error downloading")
                }
                return rssFeed
            }

            private fun downloadXML(urlPath: String?): String {
                val result = StringBuilder()
                try {
                    val url = URL(urlPath)
                    val connection = url.openConnection() as HttpURLConnection
                    val responseCode = connection.responseCode
                    Log.d(TAG, "downloadXML: The response code was $responseCode")
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val inputBuffer = CharArray(500)
                    var charsRead = 0
                    while (charsRead >= 0) {
                        charsRead = reader.read(inputBuffer)
                        if (charsRead > 0) {
                            result.append(String(inputBuffer, 0, charsRead))
                        }
                    }
                    reader.close()
                    Log.d(TAG, "Received ${result.length} bytes")
                    return result.toString()
                } catch (e: MalformedURLException) {
                    Log.e(TAG, "downloadXML: Invalid URL ${e.message}")
                } catch (e: IOException) {
                    Log.e(TAG, "downloadXML: IOException reading data ${e.message}")
                } catch (e: SecurityException) {
                    Log.e(TAG, "downloadXML: SecurityException. Needs permission? ${e.message}")
                } catch (e: Exception) {
                    Log.e(TAG, "downloadXML: Unknown error ${e.message}")
                }

                return ""
            }
        }
    }
}
