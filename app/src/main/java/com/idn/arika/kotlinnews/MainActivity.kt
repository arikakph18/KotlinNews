package com.idn.arika.kotlinnews

import com.idn.arika.kotlinnews.Interface.NewsService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.idn.arika.kotlinnews.Adapter.ViewHolder.ListSourceAdapter
import com.idn.arika.kotlinnews.Common.Common
import com.idn.arika.kotlinnews.Model.WebSite
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        init cache
        Paper.init{this}

        mService = Common.newsService

        swipe_to_refresh.setOnfreshListener{
            loadWebSiteSource(true)
        }
        recycler_view_source_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view_source_news.layoutManager = layoutManager

        dialog = spotsDialog(this)
        loadWebSiteSource(false)

    }
    private fun loadWebSiteSource(isRefresh:Boolean){
        if(!isRefresh)
        {
            val cache = Paper.book().read<String>("cache")
            if(cache !=null && !cache.isBlank() && cache !="null")
            {
//              Read cache
                val webSite = Gson().fromJson<ContactsContract.CommonDataKinds.Website>(cache, ContactsContract.CommonDataKinds.Website::class.java)
                adapter = ListSourceAdapter(baseContext, ContactsContract.CommonDataKinds.Webite)
                adapter.notifyDataSetChanged()
                recycler_view_source_news.adapter = adapter
            }
            else
            {
//                load website and write cache
                dialog.show()
//                fetch new data
                mService.sources.enqueue(object : retrofit2.Callback<WebSite>{
                    //                    ctrl+o
                    override fun onFailure(call: Call<WebSite>, t: Throwable) {
                        Toast.makeText(baseContext,"Failed",Toast.LENGTH_SHORT).show()

                    }
                    override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                        adapter = ListSourceAdapter(baseContext,response!!.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view_source_new.adapter = adapter

//                        Save to cache
                        paper.book().write("cache",Gson().toJson(response!!.body()!!))

                        dialog.dismiss()
                    }


                })
            }
        }
        else
        {
            swipe_to_refreshing=true
//            Fetch new data
            mService.sources.enqueue(object : retrofit2.Callback<WebSite>{
                //                    ctrl+o
                override fun onFailure(call: Call<WebSite>, t: Throwable) {
                    Toast.makeText(baseContext,"Failed",Toast.LENGTH_SHORT).show()

                }
                override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                    adapter = ListSourceAdapter(baseContext,response!!.body()!!)
                    adapter.notifyDataSetChanged()
                    recycler_view_source_new.adapter = adapter

//                        Save to cache
                    Paper.book().write("cache",Gson().toJson(response!!.body()!!))

                    swipe_to_refresh.isRefresh=false
                }


            })
        }
    }
    }
}