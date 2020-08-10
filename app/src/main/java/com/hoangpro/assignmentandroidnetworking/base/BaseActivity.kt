package com.hoangpro.assignmentandroidnetworking.base

import android.content.res.Resources
import android.os.Bundle
import android.os.PersistableBundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.hoangpro.assignmentandroidnetworking.model.EventMessage
import com.hoangpro.assignmentandroidnetworking.util.PreferenceHelper
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

open class BaseActivity : AppCompatActivity() {
    lateinit var preferenceHelper :PreferenceHelper
    override fun getTheme(): Resources.Theme {
        preferenceHelper = PreferenceHelper(this)
        return super.getTheme()
    }
    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }


    @Subscribe
    open fun onEventBus(eventMessage: EventMessage) {

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}