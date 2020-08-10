package com.hoangpro.assignmentandroidnetworking.util

import android.content.Context
import java.net.URL

class PreferenceHelper(context: Context) {
    private val preference = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    private val editor = preference.edit()
    companion object{
        private val PREF_NAME = "WallPaperCache"
        private val URL="URL"
        private val SCROLLABLE="SCROABLE"
        private val WIDTH="WIDTH"
        private val HEIGHT="HEIGHT"
    }

    fun setData(url:String,scrollable:Boolean){
        editor.putString(URL,url).apply()
        editor.putBoolean(SCROLLABLE,scrollable).apply()
    }

    fun setSize(width:Int,height:Int){
        editor.putInt(WIDTH,width).apply()
        editor.putInt(HEIGHT,height).apply()
    }

    fun getURL():String = preference.getString(URL,"")?:""

    fun getScrollable():Boolean  = preference.getBoolean(SCROLLABLE,false)

    fun getWidth():Int = preference.getInt(WIDTH,0)
    fun getHeight():Int = preference.getInt(HEIGHT,0)
}