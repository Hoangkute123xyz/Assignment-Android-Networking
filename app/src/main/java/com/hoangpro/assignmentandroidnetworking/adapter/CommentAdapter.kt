package com.hoangpro.assignmentandroidnetworking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hoangpro.assignmentandroidnetworking.R
import com.hoangpro.assignmentandroidnetworking.api.model.CommentData
import com.hoangpro.assignmentandroidnetworking.util.StringHelper
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter(context: Context) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    val commentList = ArrayList<CommentData.Comment>()
    val context = context
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imgAvatar = itemView.imgAvatar
        val tvUserName = itemView.tvUserName
        val tvContent = itemView.tvContent
        val tvTime = itemView.tvTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment,parent,false))
    }

    override fun getItemCount(): Int =commentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = commentList[position]
        holder.apply {
            tvUserName.text = comment.authorname
            tvContent.text = comment.Content
            tvTime.text = StringHelper.secondToTimeAgo(context,comment.datecreate.toLong())
        }
    }

    fun setData(commentList:ArrayList<CommentData.Comment>){
        this.commentList.clear()
        this.commentList.addAll(commentList)
        notifyDataSetChanged()
    }
}