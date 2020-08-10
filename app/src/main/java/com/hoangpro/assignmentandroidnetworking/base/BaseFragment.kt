package com.hoangpro.assignmentandroidnetworking.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hoangpro.assignmentandroidnetworking.model.EventMessage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BaseFragment : Fragment() {
    abstract fun getLayout():Int

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
        EventBus.getDefault().register(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(),container,false)
    }

    @Subscribe
    fun onEventBus(eventMessage: EventMessage){}
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}