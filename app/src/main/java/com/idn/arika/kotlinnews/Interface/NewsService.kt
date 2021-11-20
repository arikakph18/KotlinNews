package com.idn.arika.kotlinnews.Interface

import com.idn.arika.kotlinnews.Model.News
import com.idn.arika.kotlinnews.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {

    @get:GET("v2/sources?apiKey=5f80943b5bdf4b2699a28167d1e5a6e1")
    val sources: Call<WebSite>

    @GET
    fun getNewsFromSource(@Url url:String):Call<News>
}