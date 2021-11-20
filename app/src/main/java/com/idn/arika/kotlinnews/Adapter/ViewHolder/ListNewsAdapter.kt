package com.idn.arika.kotlinnews.Adapter.ViewHolder

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.internal.bind.util.ISO8601Utils
import com.idn.arika.kotlinnews.Common.ISO8601Parser
import com.idn.arika.kotlinnews.Interface.ItemClickListener
import com.idn.arika.kotlinnews.Model.Article
import com.idn.arika.kotlinnews.NewsDetail
import com.idn.arika.kotlinnews.R
import com.squareup.picasso.Picasso
import java.text.ParseException


class ListNewsAdapter(val articleList:MutableList<Article>,private val context: Context):RecyclerView.Adapter<ListNewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val itemView = inflater.inflate(R.layout.news_layout, parent, false)
        return ListNewsViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return articleList.size
}

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
//        load image
        Picasso.with(context)
            .load(articleList[position].urlToImage)
            .into(holder.article_image)

        if (articleList[position].title!!.length > 65) {
            holder.article_title.text = articleList[position].title!!.substring(0, 65) + "..."
        } else {
            holder.article_title.text = articleList[position].title!!
        }
        if (articleList[position].publishedAt !=null)
        {
            var date: ContactsContract.Data?=null
            try {
                date = ISO8601Parser.parse(articleList[position].publishedAt!!)
            }catch (ex:ParseException)
            {
                ex.printStackTrace()
            }
        }

        holder.article_time.setReferenceTime(date!!.time)

//        set event click
        holder.setItemClickListener(object : ItemClickListener{
            override fun onClick(view: View, position: Int) {
//
                val detail = Intent(context,NewsDetail::class.java)
                detail.putExtra("weURL",articleList[position].url)
                context.startActivity(detail)
            }

        }


    }



}