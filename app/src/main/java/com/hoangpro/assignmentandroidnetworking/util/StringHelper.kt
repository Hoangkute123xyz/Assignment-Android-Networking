package com.hoangpro.assignmentandroidnetworking.util

import android.content.Context
import com.hoangpro.assignmentandroidnetworking.R
import java.text.SimpleDateFormat
import java.util.*

class StringHelper {
    companion object{
        fun secondToTimeAgo(context: Context, second: Long): String {
            val currentSecond = System.currentTimeMillis() / 1000
            val offsetTime = currentSecond - second
            when {
                offsetTime<30->{
                    return context.getString(R.string.just_now)
                }
                offsetTime < 60 -> {
                    return "$offsetTime ${context.getString(R.string.second_ago)}"
                }
                offsetTime < 3600 -> {
                    return "${offsetTime / 60} ${context.getString(R.string.minute_ago)}"
                }
                offsetTime < 86400 -> {
                    return "${offsetTime / 3600} ${context.getString(R.string.hour_ago)}"
                }
                offsetTime < 2592000 -> {
                    return "${offsetTime / 86400} ${context.getString(R.string.day_ago)}"
                }
                else -> {
                    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm")
                    return simpleDateFormat.format(Date(second * 1000))
                }
            }
        }
    }
}