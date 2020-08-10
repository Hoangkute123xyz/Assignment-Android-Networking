package com.hoangpro.assignmentandroidnetworking.fragment.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hoangpro.assignmentandroidnetworking.R
import com.hoangpro.assignmentandroidnetworking.adapter.CommentAdapter
import com.hoangpro.assignmentandroidnetworking.api.PhotoClient
import com.hoangpro.assignmentandroidnetworking.api.model.PhotoData
import com.hoangpro.assignmentandroidnetworking.base.BaseFullScreenBSDF
import com.hoangpro.assignmentandroidnetworking.util.AnimationHelper
import kotlinx.android.synthetic.main.bsdf_comment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ViewCommentBSDF : BaseFullScreenBSDF() {
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var photo: PhotoData.Photo
    override fun getLayout(): Int {
        return R.layout.bsdf_comment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        initData()
    }

    private fun initData() {
        coroutineScope = CoroutineScope(Dispatchers.Default)
        pgLoading.visibility=View.VISIBLE
        tvPlaceHolder.visibility=View.GONE
        coroutineScope.launch {
            PhotoClient.getCommentOfPhoto(photo.id,{
                commentAdapter.setData(it.comments.comment)
                pgLoading.visibility=View.GONE
                rvComment.visibility=View.VISIBLE
            },{
                pgLoading.visibility=View.GONE
                tvPlaceHolder.visibility=View.VISIBLE
                rvComment.visibility=View.GONE
            })
        }
    }

    private fun setupView() {
        commentAdapter = CommentAdapter(requireContext())
        rvComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        AnimationHelper.scaleClickAnimation(imgClose){
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    fun showComment(fm: FragmentManager, photo: PhotoData.Photo) {
        if (!isAdded)
            show(fm, tag)
        this.photo = photo
    }
}