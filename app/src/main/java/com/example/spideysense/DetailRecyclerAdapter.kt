package com.example.spideysense

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.comic_item.view.*

class DetailRecyclerAdapter(val listener: (Character.Data.Result.Comics.Item) -> Unit): RecyclerView.Adapter<DetailRecyclerAdapter.ViewHolder>() {
    private var list: List<Character.Data.Result.Comics.Item> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.comic_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener(list[position]) }
        holder.label.text = list[position].name
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun clearData() {
        list = listOf()
    }

    fun swapData(details: List<Character.Data.Result.Comics.Item>) {
        list = details
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val label = itemView.comicName
    }
}