package com.dm.shopdemo.networkre

import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import java.io.BufferedOutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader


class NetworkRequest() {

    fun makeRequest(req: String, endpoint: String, callBack: NetworkCallBack,progressBar: ProgressBar) {
        class MyHttpRequestTask(callBack: NetworkCallBack) : AsyncTask<String, Void, String>() {

            var mCallBack: NetworkCallBack? = null
            var mException: Exception? = null
            var baseurl = "https://private-anon-745938ad7c-ddshop.apiary-mock.com/"

            init {
                this.mCallBack = callBack
            }

            override fun onPreExecute() {
                super.onPreExecute()
            }

            override fun doInBackground(vararg params: String): String? {
                var response: String = ""
                try {
                    val url = URL(baseurl + endpoint)
                    val httpURLConnection = url.openConnection() as HttpURLConnection
                    httpURLConnection.requestMethod = "GET"
//                    httpURLConnection.setRequestProperty("Content-Type", "application/json")
                    try {
//                        httpURLConnection.doOutput = true
                        httpURLConnection.connect()
                        val status = httpURLConnection.responseCode

                        when (status) {
                            200, 201 -> {
                                val br =
                                    BufferedReader(InputStreamReader(httpURLConnection.inputStream))
                                val sb = StringBuilder()
                                var line : String?
                                do {
                                    line = br.readLine()
                                    if (line == null)
                                        break
                                    sb.append(line)
                                } while (true)
                                br.close()
                                response = sb.toString()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        mException = e
                    } finally {
                        httpURLConnection.disconnect()
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                    mException = e
                }

                return response
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                progressBar!!.setVisibility(View.GONE)
                mException?.let {
                    mCallBack?.onFailure(it)
                } ?: run {
                    if (result != null) {
                        mCallBack?.onSuccess(result)
                    }
                }
            }
        }
        MyHttpRequestTask(callBack).execute()
    }
}