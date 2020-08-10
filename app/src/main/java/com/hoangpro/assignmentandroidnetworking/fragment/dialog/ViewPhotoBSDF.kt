package com.hoangpro.assignmentandroidnetworking.fragment.dialog

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.snackbar.Snackbar
import com.hoangpro.assignmentandroidnetworking.R
import com.hoangpro.assignmentandroidnetworking.api.PhotoClient
import com.hoangpro.assignmentandroidnetworking.api.model.PhotoData
import com.hoangpro.assignmentandroidnetworking.base.BaseFullScreenBSDF
import com.hoangpro.assignmentandroidnetworking.service.DownLoadPhotoServices
import com.hoangpro.assignmentandroidnetworking.service.SetWallpaperWorker
import com.hoangpro.assignmentandroidnetworking.util.AnimationHelper
import com.hoangpro.assignmentandroidnetworking.util.AppUtil
import kotlinx.android.synthetic.main.bsdf_view_photo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.math.abs

class ViewPhotoBSDF : BaseFullScreenBSDF() {
    private lateinit var photo: PhotoData.Photo
    private lateinit var coroutineScope:CoroutineScope
    override fun getLayout(): Int = R.layout.bsdf_view_photo
    private val commentBSDF = ViewCommentBSDF()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        coroutineScope = CoroutineScope(Dispatchers.Default)
        tvTitleToolbar.text = photo.title
        Glide.with(requireContext()).load(photo.urlL).placeholder(R.drawable.ic_outline_image_24)
            .into(photoView)

        val anim1 = AnimationHelper.moveView(lnBarBottom, AnimationHelper.VERTICAL, 1000f, 0f)
        val anim2 = AnimationHelper.moveView(lnBarRight, AnimationHelper.HORIZONTAL, 1000f, 0f)
        coroutineScope.launch {
            PhotoClient.getPhotoDetail(photo.id,{
                anim1.start()
                anim2.start()
                tvCommentCount.text = it.photo.comments.Content.toString()
                tvViewCount.text = it.photo.views
            },{

            })
        }

        AnimationHelper.scaleClickAnimation(imgDownload) {
            val dataList = ArrayList<String>()
            dataList.add("${photo.widthS} x ${photo.heightS}")
            dataList.add("${photo.widthM} x ${photo.heightM}")
            dataList.add("${photo.widthL} x ${photo.heightL}")

            val urlList = ArrayList<String>()
            urlList.add(photo.urlS)
            urlList.add(photo.urlM)
            urlList.add(photo.urlL)
            AppUtil.showMultiChoiceDialog(requireContext(), dataList) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!AppUtil.checkPermission(
                            requireContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                        AppUtil.requestPermission(
                            requireActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            AppUtil.EXTERNAL_STORAGE_PERMISSION
                        )
                        return@showMultiChoiceDialog
                    }
                }
                val intent = Intent(requireContext(), DownLoadPhotoServices::class.java)
                intent.putExtra(DownLoadPhotoServices.URL_PARAM_NAME, urlList[it])
                requireActivity().startService(intent)
            }
        }

        AnimationHelper.scaleClickAnimation(imgWallpaper) {
            Glide.with(requireContext()).asBitmap().load(photo.urlL)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val dataList = ArrayList<String>()
                        dataList.add(getString(R.string.scroll))
                        dataList.add(getString(R.string.no_scroll))
                        dataList.add(getString(R.string.scroll_with_time))
                        dataList.add(getString(R.string.no_scroll_with_time))
                        AppUtil.showMultiChoiceDialog(requireContext(), dataList) {
                            when (it) {
                                0 -> AppUtil.setWallpaper(requireActivity(), resource, true)
                                1 -> AppUtil.setWallpaper(requireActivity(), resource, false)
                                2 -> {
                                    getTime {time->
                                        SetWallpaperWorker.requestSetWallpaperJob(
                                            requireContext(),
                                            photo.urlL,
                                            true,
                                            time/1000,
                                            TimeUnit.SECONDS
                                        )
                                        Toast.makeText(requireContext(),R.string.set_schduler_successfully,Toast.LENGTH_SHORT).show()
                                    }
                                }
                                3 -> {
                                    getTime {time->
                                        SetWallpaperWorker.requestSetWallpaperJob(
                                            requireContext(),
                                            photo.urlL,
                                            false,
                                            time/1000,
                                            TimeUnit.SECONDS
                                        )
                                        Toast.makeText(requireContext(),R.string.set_schduler_successfully,Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }

                })
        }

        AnimationHelper.scaleClickAnimation(imgComment){
            commentBSDF.showComment(childFragmentManager,photo)
        }

        AnimationHelper.scaleClickAnimation(imgClose){
            dismiss()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            AppUtil.EXTERNAL_STORAGE_PERMISSION -> {
                val intent = Intent(requireContext(), DownLoadPhotoServices::class.java)
                intent.putExtra(DownLoadPhotoServices.URL_PARAM_NAME, photo.urlL)
                requireActivity().startService(intent)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun showPhoto(fm: FragmentManager, photo: PhotoData.Photo) {
        this.photo = photo
        show(fm, tag)
    }

    fun getTime(onResult:(Long)->Unit){
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(requireContext(),{_,year,month,dayOfMonth->
            val timePicker = TimePickerDialog(requireContext(),{_,hour,minute->
                val format = SimpleDateFormat("dd/MM/yyyy - HH:mm")
                val date = format.parse("$dayOfMonth/${month+1}/$year - $hour:$minute")
                onResult(abs(System.currentTimeMillis() - date.time))
            },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true)
            timePicker.show()
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}