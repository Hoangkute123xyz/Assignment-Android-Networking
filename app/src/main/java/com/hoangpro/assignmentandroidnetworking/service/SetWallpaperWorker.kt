package com.hoangpro.assignmentandroidnetworking.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.work.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.hoangpro.assignmentandroidnetworking.util.AppUtil
import com.hoangpro.assignmentandroidnetworking.util.PreferenceHelper
import java.util.concurrent.TimeUnit

class SetWallpaperWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    private val context = context
    private val preferenceHelper = PreferenceHelper(context)

    companion object {
        fun requestSetWallpaperJob(
            context: Context,
            url: String,
            scrollable: Boolean,
            timeOffset: Long,
            timeUnit: TimeUnit
        ) {
            val preferenceHelper = PreferenceHelper(context)
            preferenceHelper.setData(url, scrollable)
            val workBuilder = OneTimeWorkRequestBuilder<SetWallpaperWorker>()
                .setInitialDelay(timeOffset, timeUnit)
                .addTag("SetWallpaperWorker")
                .build()
            try {
                WorkManager.getInstance(context)
                    .enqueueUniqueWork("set wallpaper now", ExistingWorkPolicy.REPLACE, workBuilder)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    override fun doWork(): Result {
        Log.e("excute", "doWork")
        Glide.with(context).asBitmap().load(preferenceHelper.getURL()).into(object :
            CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                AppUtil.setWallpaper(context, resource, preferenceHelper.getScrollable())
            }
        })

        return Result.success()
    }
}