package com.idn.arika.kotlinnews

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.idn.arika.kotlinnews.Adapter.ViewHolder.ListNewsAdapter
import com.idn.arika.kotlinnews.Common.Common
import com.idn.arika.kotlinnews.Interface.NewsService
import com.idn.arika.kotlinnews.Model.News
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {

    var source=""
    var webHotUrl:String?=""

    lateinit var dialog:AlertDialog
    lateinit var mService:NewsService
    lateinit var adapter:ListNewsAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        //init view
        mService = Common.newsService

        dialog = SpotsDialog(this)

        swipe_to_refresh.setOnRefreshListener( loadNews(source, true))

        diagonalLayout.setOnClikListener{
            val detail = Intent(baseContext,NewsDetail::class.java)
        detail.putExtra("weURL",webHotUrl)
        .startActivity(detail)
    }
        list_news.setHasPixedSize(true)
        list_news.layoutManager = LinearLayoutManager(this)

        if (intent != null)
        {
            source = intent.getStringExtra("source")
            if (!source.isEmpty())
                loadNews(source, false)
        }

    }

    private fun loadNews(source: String?, isRefresh: Boolean) {
        if (isRefreshed)
        {
            dialog.show()
            mService.getNewsFromSource(Common.getNewsApi(source!!))
                    .enqueue(object : Callback<News>{
                        override fun onFailure(call: Call<News>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                        override fun onResponse(call: Call<News>, response: Response<News>) {
                            dialog.dismiss()
//                            get first article to not news
                            Picasso.with(baseContext)
                                    .load(response.body()!!.articles!![0].urlToImage)
                                    .into(top_image)

                            top_title.text = response.body()!!.articles!![0].title
                            top_author.text = response.body()!!.articles!![0].author

                            webHotUrl = response.body()!!.articles!![0].url
//                           load all remain articles
                            val removeFirstItem = response.body()!!.articles

// because we get first item to hot show
                            removeFirstItem.removeAt(0)

                            adapter = ListNewsAdapter(removeFirstItem!!,baseContext)
                            adapter.notifyDataSetChanged()
                            list_news.adapter = adapter = adapter
                        }


                    })
        }
        else
        {
            swipe_to_refresh.isRefreshing = true
            mService.getNewsFromSource(Common.getNewsApi(source!!))
                    .enqueue(object : Callback<News>{
                        override fun onFailure(call: Call<News>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                        override fun onResponse(call: Call<News>, response: Response<News>) {
                           swipe_to_refresh.isRefreshing = false
//                            get first article to not news
                            Picasso.with(baseContext)
                                    .load(response!!.body()!!.articles!![0].urlToImage)
                                    .into(top_image)

                            top_title.text = response!!.body()!!.articles!![0].title
                            top_author.text = response!!.body()!!.articles!![0].author

                            webHotUrl = response!!.body()!!.articles!![0].url
//                           load all remain articles
                            val removeFirstItem!! = response.body()!!.articles

// because we get first item to hot show
                            removeFirstItem!!.removeAt(0)

                            adapter = ListNewsAdapter(removeFirstItem!!,baseContext)
                            adapter.notifyDataSetChanged()
                            list_news.adapter = adapter = adapter
                        }


                    })
        }

    }
}