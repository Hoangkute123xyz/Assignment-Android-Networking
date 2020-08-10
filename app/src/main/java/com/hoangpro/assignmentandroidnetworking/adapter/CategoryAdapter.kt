package com.hoangpro.assignmentandroidnetworking.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hoangpro.assignmentandroidnetworking.R
import com.hoangpro.assignmentandroidnetworking.api.model.CategoryData
import com.hoangpro.assignmentandroidnetworking.util.AnimationHelper
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(context: Context,onItemClick:(Int)->Unit) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private val onItemClick = onItemClick
    private val context = context
    private val categoryList = ArrayList<CategoryData.Gallery>()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imgCover1 = itemView.imgCover1
        val imgCover2 = itemView.imgCover2
        val imgCover3 = itemView.imgCover3
        val tvView = itemView.tvView
        val tvPhoto = itemView.tvPhoto
        val tvTitle = itemView.tvTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category,parent,false))
    }

    override fun getItemCount(): Int =categoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gallery = categoryList[position]
        val photoCover = gallery.coverPhotos.photo
        holder.apply {
            AnimationHelper.scaleClickAnimation(itemView){
                onItemClick(position)
            }
            tvPhoto.text = gallery.countPhotos.toString()
            tvView.text = gallery.countViews.toString()
            tvTitle.text = gallery.title.Content
            Glide.with(context).load(photoCover[0].url).into(imgCover3)
            if (photoCover.size<2) return@apply
            Glide.with(context).load(photoCover[1].url).into(imgCover1)
            if (photoCover.size<3) return@apply
            Glide.with(context).load(photoCover[2].url).into(imgCover2)
        }
    }

    fun replaceData(categoriesList:ArrayList<CategoryData.Gallery>){
        categoryList.clear()
        categoryList.addAll(categoriesList)
        notifyDataSetChanged()
    }

}