package com.hoangpro.assignmentandroidnetworking.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hoangpro.assignmentandroidnetworking.R
import com.hoangpro.assignmentandroidnetworking.adapter.PhotoAdapter
import com.hoangpro.assignmentandroidnetworking.api.PhotoClient
import com.hoangpro.assignmentandroidnetworking.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_lastest.*
import kotlinx.android.synthetic.main.fragment_lastest.pgLoadMore
import kotlinx.android.synthetic.main.fragment_lastest.rvPhoto
import kotlinx.android.synthetic.main.fragment_lastest.swipeToRefresh
import kotlinx.android.synthetic.main.fragment_more.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FragmentMore : BaseFragment() {
    override fun getLayout(): Int = R.layout.fragment_more
    private var currentPage = 1
    private lateinit var photoAdapter: PhotoAdapter
    private var canLoadMore = true
    private var maxPage = 1
    private lateinit var coroutineScope: CoroutineScope
    private var currentSearchText=""
    private var canQuery=true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        coroutineScope = CoroutineScope(Dispatchers.Default)
        photoAdapter = PhotoAdapter(requireContext())
        rvPhoto.adapter = photoAdapter
        rvPhoto.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvPhoto.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val arr = IntArray(2)
                val staggeredGridLayoutManager = rvPhoto.layoutManager as StaggeredGridLayoutManager
                staggeredGridLayoutManager.findLastVisibleItemPositions(arr)
                if (arr[1] == photoAdapter.itemCount - 2) {
                    moveToNextPage(currentSearchText)
                }
            }
        })
        swipeToRefresh.setOnRefreshListener {
            searchText(currentSearchText)
        }
        edtSearch.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (canQuery && s.toString().isNotEmpty()){
                    searchText(s.toString())
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    private fun searchText(text: String) {
        canQuery=false
        currentSearchText=text
        swipeToRefresh.isRefreshing = true
        coroutineScope.launch {
            PhotoClient.searchPhoto(
                text,
                1,
                {
                    if (it != null) {
                        currentPage = 1
                        canLoadMore = true
                        photoAdapter.replaceData(it.photos.photo)
                        maxPage = it.photos.pages
                    }
                    swipeToRefresh.isRefreshing = false
                    if (text!=edtSearch.text.toString()){
                        searchText(edtSearch.text.toString())
                        return@searchPhoto
                    }
                    canQuery=true
                }, {
                    swipeToRefresh.isRefreshing = false
                    canQuery=true
                }
            )
        }
    }

    private fun moveToNextPage(text: String) {
        if (canLoadMore && currentPage < maxPage) {
            canLoadMore = false
            pgLoadMore.visibility = View.VISIBLE
            swipeToRefresh.isRefreshing = true
            coroutineScope.launch {
                PhotoClient.searchPhoto(text, currentPage + 1, {
                    currentPage++
                    photoAdapter.addData(it.photos.photo)
                    canLoadMore = true
                    swipeToRefresh.isRefreshing = false
                    pgLoadMore.visibility = View.GONE
                }, {
                    canLoadMore = true
                    swipeToRefresh.isRefreshing = false
                    pgLoadMore.visibility = View.GONE
                })
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}