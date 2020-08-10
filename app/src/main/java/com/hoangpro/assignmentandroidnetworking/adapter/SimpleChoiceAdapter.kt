package com.hoangpro.assignmentandroidnetworking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hoangpro.assignmentandroidnetworking.R
import com.hoangpro.assignmentandroidnetworking.util.AnimationHelper
import kotlinx.android.synthetic.main.item_simple_text.view.*

class SimpleChoiceAdapter(context: Context,dataList:ArrayList<String>,onItemClick:(Int)->Unit) : RecyclerView.Adapter<SimpleChoiceAdapter.ViewHolder>() {
    private val context = context
    private val onItemClick = onItemClick
    private val dataList = dataList
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvContent = itemView.tvContent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_simple_text,parent,false))
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            tvContent.text  =  dataList[position]
            AnimationHelper.scaleClickAnimation(tvContent){
                onItemClick(position)
            }
        }
    }
}
