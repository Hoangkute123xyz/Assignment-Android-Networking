package com.hoangpro.assignmentandroidnetworking.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hoangpro.assignmentandroidnetworking.R
import com.hoangpro.assignmentandroidnetworking.adapter.PhotoAdapter
import com.hoangpro.assignmentandroidnetworking.api.PhotoClient
import com.hoangpro.assignmentandroidnetworking.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_lastest.*
import kotlin.math.max

class LatestFragment :BaseFragment(){

    private var currentPage=1
    private lateinit var photoAdapter: PhotoAdapter
    private var canLoadMore = true
    private var maxPage =1

    override fun getLayout(): Int = R.layout.fragment_lastest

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        initData()
    }

    private fun setupView() {
        photoAdapter = PhotoAdapter(requireContext())
        rvPhoto.adapter=photoAdapter
        rvPhoto.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        rvPhoto.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val arr = IntArray(2)
                val staggeredGridLayoutManager = rvPhoto.layoutManager as StaggeredGridLayoutManager
                staggeredGridLayoutManager.findLastVisibleItemPositions(arr)
                if (arr[1]==photoAdapter.itemCount-2){
                    moveToNextPage()
                }
            }
        })
        swipeToRefresh.setOnRefreshListener {
            initData()
        }
    }
    private fun initData(){
        swipeToRefresh.isRefreshing=true
        PhotoClient.getAllFavoritePhotos(
            1,
            {
                if (it!=null){
                    currentPage=1
                    canLoadMore=true
                    photoAdapter.replaceData(it.photos.photo)
                    maxPage = it.photos.pages
                }
                swipeToRefresh.isRefreshing=false
            },{
                swipeToRefresh.isRefreshing=false
            }
        )
    }

    private fun moveToNextPage(){
        if(canLoadMore && currentPage<maxPage){
            canLoadMore=false
            pgLoadMore.visibility=View.VISIBLE
            swipeToRefresh.isRefreshing=true
            PhotoClient.getAllFavoritePhotos(currentPage+1,{
                if (it!=null){
                    currentPage++
                    photoAdapter.addData(it.photos.photo)
                }
                canLoadMore=true
                swipeToRefresh.isRefreshing=false
                pgLoadMore.visibility=View.GONE
            },{
                canLoadMore = true
                swipeToRefresh.isRefreshing=false
                pgLoadMore.visibility=View.GONE
            })
        }

    }
}