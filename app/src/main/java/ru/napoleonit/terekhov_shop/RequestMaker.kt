package ru.napoleonit.terekhov_shop

import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

interface RequestMaker {
    fun make(url: String, onResult: (result: String) -> Unit)
}

interface DatabaseManager {
    fun readDatabase(path: String): String
}

//class NativeRequestMaker : RequestMaker {
//
//    override fun make(url: String): String {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}

class OkHttpRequestMaker : RequestMaker {

    override fun make(url: String, onResult: (result: String) -> Unit): Unit = run {
        GlobalScope.launch(Dispatchers.Main) {

            val resultDeferred = GlobalScope.async(Dispatchers.IO) {
                makeRequest(url)
            }

            val result = resultDeferred.await()

            onResult(result)
        }
    }

    private fun makeRequest(url: String) = run {

        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()

        val response = client.newCall(request).execute()

        response.body()!!.string()
    }

}