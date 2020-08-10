package com.hoangpro.assignmentandroidnetworking.util

import android.app.Activity
import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Point
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Display
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoangpro.assignmentandroidnetworking.R
import com.hoangpro.assignmentandroidnetworking.adapter.SimpleChoiceAdapter


class AppUtil {
    companion object {
        val EXTERNAL_STORAGE_PERMISSION = 0

        @RequiresApi(Build.VERSION_CODES.M)
        fun checkPermission(context: Context, permission: String): Boolean {
            return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        }

        fun requestPermission(activity: Activity, permission: String, permissionCode: Int) {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), permissionCode)
        }


        fun showMultiChoiceDialog(
            context: Context,
            dataList: ArrayList<String>,
            onItemClick: (Int) -> Unit
        ) {
            val builder = AlertDialog.Builder(context)
            val view = LayoutInflater.from(context)
                .inflate(R.layout.dialog_multichoice_simple, null, false)
            val rvChoice = view.findViewById<RecyclerView>(R.id.rvChoice)
            builder.setView(view)
            val alertDialog = builder.create()
            val simpleChoiceAdapter = SimpleChoiceAdapter(context, dataList) {
                onItemClick(it)
                alertDialog.dismiss()
            }
            builder.setView(view)
            rvChoice.apply {
                adapter = simpleChoiceAdapter
                layoutManager = LinearLayoutManager(context)
            }
            alertDialog.show()
        }

        fun setWallpaper(context: Context, bitmap: Bitmap, isScrollable: Boolean) {
            val preferenceHelper = PreferenceHelper(context)
            val wallpaperManager = WallpaperManager.getInstance(context)
            try {
                if (isScrollable) {
                    wallpaperManager.setBitmap(bitmap)
                } else {
//                    val screenHeight = size.y

//                    var width: Int = bitmap.width
//                    width = width * screenHeight / bitmap.height
//                    var height: Int = bitmap.width
//                    height = height * size.y / bitmap.width

                    wallpaperManager.setWallpaperOffsetSteps(1f,1f)
                    wallpaperManager.setBitmap(
                        getResizedBitmap(bitmap,preferenceHelper.getWidth()-100,preferenceHelper.getHeight()-100)
                    )
                }
                Handler().post {
                    Toast.makeText(context, R.string.set_wallpaper_successfully, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Handler().post {
                    Toast.makeText(context, R.string.an_occured_error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
            val width = bm.width
            val height = bm.height
            val scaleWidth = newWidth.toFloat() / width
            val scaleHeight = newHeight.toFloat() / height
            val matrix = Matrix()
            matrix.postScale(scaleWidth, scaleHeight)

            val resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false
            )
            bm.recycle()
            return resizedBitmap
        }
    }
}