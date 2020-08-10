package com.hoangpro.assignmentandroidnetworking.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hoangpro.assignmentandroidnetworking.R
import com.hoangpro.assignmentandroidnetworking.adapter.CategoryAdapter
import com.hoangpro.assignmentandroidnetworking.adapter.PhotoAdapter
import com.hoangpro.assignmentandroidnetworking.api.PhotoClient
import com.hoangpro.assignmentandroidnetworking.api.model.CategoryData
import com.hoangpro.assignmentandroidnetworking.api.model.PhotoData
import com.hoangpro.assignmentandroidnetworking.base.BaseFragment
import com.hoangpro.assignmentandroidnetworking.util.AnimationHelper
import kotlinx.android.synthetic.main.fragment_category_child.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CategoryFragment() : BaseFragment() {
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var coroutineScope: CoroutineScope
    private var categoryData: CategoryData? = null
    private lateinit var currentGallery: CategoryData.Gallery
    private lateinit var photoAdapter: PhotoAdapter
    private var photoData: PhotoData? = null
    private var currentPhotoPage = 1
    private var canLoadMore = true
    override fun getLayout(): Int {
        return R.layout.fragment_category_child
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        categoryAdapter = CategoryAdapter(requireContext()) {
            currentGallery = categoryData!!.galleries.gallery[it]
            currentPhotoPage = 1
            setupForPhotoView()
        }
        rvCategory.apply {
            adapter = categoryAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
        coroutineScope = CoroutineScope(Dispatchers.Default)
        swipeToRefresh.setOnRefreshListener {
            initData(true)
        }
        photoAdapter = PhotoAdapter(requireContext())
        rvPhoto.apply {
            adapter = photoAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        rvPhoto.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = rvPhoto.layoutManager as StaggeredGridLayoutManager
                val posArr = IntArray(2)
                layoutManager.findLastVisibleItemPositions(posArr)
                if (photoData == null) return
                if (posArr[1] == photoData!!.photos.photo.size - 2 && canLoadMore && currentPhotoPage < photoData!!.photos.pages) {
                    canLoadMore = false
                    pgLoadMore.visibility = View.VISIBLE
                    swipeToRefresh.isRefreshing = true
                    coroutineScope.launch {
                        PhotoClient.getPhotosOfGallery(
                            currentGallery.galleryId,
                            currentPhotoPage + 1,
                            { photoData ->
                                swipeToRefresh.isRefreshing = false
                                pgLoadMore.visibility = View.GONE
                                this@CategoryFragment.photoData = photoData
                                photoAdapter.addData(photoData.photos.photo)
                                currentPhotoPage++
                            },
                            {
                                swipeToRefresh.isRefreshing = false
                                pgLoadMore.visibility = View.GONE
                            })
                    }
                }
            }
        })

        AnimationHelper.scaleClickAnimation(imgBack){
            setupForCategoryView()
        }
    }

    private fun setupForPhotoView() {
        currentPhotoPage = 1
        relaToobar.visibility = View.VISIBLE
        rvPhoto.visibility = View.VISIBLE
        rvCategory.visibility = View.GONE
        tvTitleToolbar.text = currentGallery.title.Content
        swipeToRefresh.isRefreshing = true
        coroutineScope.launch {
            PhotoClient.getPhotosOfGallery(
                currentGallery.galleryId,
                currentPhotoPage,
                { photoData ->
                    this@CategoryFragment.photoData = photoData
                    photoAdapter.replaceData(photoData.photos.photo)
                    swipeToRefresh.isRefreshing = false
                },
                {
                    swipeToRefresh.isRefreshing = false
                })
        }
    }

    private fun setupForCategoryView(){
        rvPhoto.visibility = View.GONE
        rvCategory.visibility=View.VISIBLE
        relaToobar.visibility=View.GONE
    }


    fun initData(requestRefresh: Boolean = false) {
        if (requestRefresh || categoryData == null) {
            swipeToRefresh.isRefreshing = true
            coroutineScope.launch {
                PhotoClient.getCategories(1, {
                    if (it != null) {
                        categoryData = it
                        categoryAdapter.replaceData(it.galleries.gallery)
                        swipeToRefresh.isRefreshing = false
                    }
                }, {
                    swipeToRefresh.isRefreshing = false
                })
            }
        }
    }

    fun onBackPressed():Boolean{
        if (relaToobar.visibility==View.VISIBLE){
            setupForCategoryView()
            return true
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}