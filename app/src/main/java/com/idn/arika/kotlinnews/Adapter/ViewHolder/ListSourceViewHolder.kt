package com.idn.arika.kotlinnews.Adapter.ViewHolder

import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.idn.arika.kotlinnews.Interface.ItemClickListener

class ListSourceViewHolder(itemView:View):RecyclerView.ViewHolder(itemView), View.OnClickListener{

    private lateinit var itemClickListener:ItemClickListener

    var source_title = itemView.source_news_name

    fun getItemClickListener(itemClickListener: ItemClickListener)
    {
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!,adapterPosition)
    }

}

