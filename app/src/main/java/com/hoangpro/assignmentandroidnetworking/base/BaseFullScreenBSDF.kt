package com.hoangpro.assignmentandroidnetworking.base

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseFullScreenBSDF :BottomSheetDialogFragment(){
    abstract fun getLayout():Int
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(),container,false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val bottomSheetDialog = dialog as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val frame = bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) ?: return@setOnShowListener
            val metric = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(metric)
            frame.layoutParams.height=metric.heightPixels
            frame.requestLayout()
            val behavior = BottomSheetBehavior.from(frame)
            behavior.peekHeight = metric.heightPixels
            behavior.isHideable=true
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    if (newState!=BottomSheetBehavior.STATE_COLLAPSED){
//                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
//                    }
//                    if (newState==BottomSheetBehavior.STATE_HIDDEN){
//                        dismiss()
//                    }
                }

            })
            behavior.state=BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }
}