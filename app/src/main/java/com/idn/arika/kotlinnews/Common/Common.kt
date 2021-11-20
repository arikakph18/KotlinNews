package com.idn.arika.kotlinnews.Common

import com.idn.arika.kotlinnews.Interface.NewsService
import com.idn.arika.kotlinnews.Remote.RetrofitClient

object Common {
    val BASE_URL = "https://newsapi.org/"
    val API_KEY="5f80943b5bdf4b2699a28167d1e5a6e1"

    val newsService:NewsService
    get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsApi(source:String,):String{
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
                .append(source)
                .append("&apiKey")
                .append(API_KEY)
                .toString()
        return apiUrl
    }


}