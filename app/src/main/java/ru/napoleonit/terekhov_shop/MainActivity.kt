package ru.napoleonit.terekhov_shop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main) {

            val okDeferred = GlobalScope.async(Dispatchers.IO) {
                delay(3000)
                "OK"
            }

            val vkDeferred = GlobalScope.async(Dispatchers.IO) {
                delay(3000)
                "VK"
            }

            val fbDeferred = GlobalScope.async(Dispatchers.IO) {
                delay(3000)
                "FB"
            }

            val list = listOf(1, 2, 3)

            Log.i("MainActivity", "list=$list")

            // [Product@1234124, Product@2314234]

            val okResult = okDeferred.await()
            val vkResult = vkDeferred.await()
            val fbResult = fbDeferred.await()

            loginButton.text = "$okResult $vkResult $fbResult"
        }

        loginButton.onClick {
            startActivity<ProductsActivity>()
        }
    }
}
