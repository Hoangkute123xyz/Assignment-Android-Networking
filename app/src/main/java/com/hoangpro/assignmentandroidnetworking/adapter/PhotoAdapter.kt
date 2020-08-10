package com.hoangpro.assignmentandroidnetworking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hoangpro.assignmentandroidnetworking.R
import com.hoangpro.assignmentandroidnetworking.api.model.PhotoData
import com.hoangpro.assignmentandroidnetworking.fragment.dialog.ViewPhotoBSDF
import com.hoangpro.assignmentandroidnetworking.util.AnimationHelper
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter(context: Context) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    private val context=context
    private val photoList = ArrayList<PhotoData.Photo>()
    private val viewPhotoBSDF=ViewPhotoBSDF()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgData = itemView.imgData
        val tvName = itemView.tvName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_photo,parent,false))
    }

    override fun getItemCount(): Int = photoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photoList[position]
        Glide.with(context).load(photo.urlM).placeholder(R.drawable.ic_outline_image_24).into(holder.imgData)
        holder.tvName.text = photo.title
        AnimationHelper.scaleClickAnimation(holder.itemView){
            if(!viewPhotoBSDF.isAdded){
                viewPhotoBSDF.showPhoto((context as AppCompatActivity).supportFragmentManager,photo)
            }
        }
    }

    fun replaceData(photoList:ArrayList<PhotoData.Photo>){
        this.photoList.clear()
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }

    fun addData(photoList: ArrayList<PhotoData.Photo>){
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }
}