package com.dm.shopdemo.networkre

import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import java.net.HttpURLConnection
import java.net.URL

import java.io.*


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
                    if(endpoint.equals(EndPoints.PRODCUTS)){
                        httpURLConnection.requestMethod = "GET"
                    }else if(endpoint.equals(EndPoints.CART)){
                        httpURLConnection.requestMethod = "POST"
                        val params = HashMap<String,String>()
                        params.put("productId","1")

                        val os = httpURLConnection.getOutputStream()
                        val writer = BufferedWriter(
                            OutputStreamWriter(os, "UTF-8")
                        )
                        writer.write(params.toString())
                        writer.flush()
                        writer.close()
                        os.close()
                    }else if(endpoint.equals(EndPoints.CART_DELETE)){
                        httpURLConnection.requestMethod = "DELETE"
                    }


                    try {
                        httpURLConnection.connect()
                        val status = httpURLConnection.responseCode

                        when (status) {
                            200, 201,204 -> {
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