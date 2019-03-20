package ru.napoleonit.terekhov_shop

import android.content.Context

fun getRequestMaker(context: Context): RequestMaker = OkHttpRequestMaker(context)