package com.hoangpro.assignmentandroidnetworking.activity

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.fxn.OnBubbleClickListener
import com.hoangpro.assignmentandroidnetworking.R
import com.hoangpro.assignmentandroidnetworking.adapter.ViewPagerAdapter
import com.hoangpro.assignmentandroidnetworking.base.BaseActivity
import com.hoangpro.assignmentandroidnetworking.fragment.CategoryFragment
import com.hoangpro.assignmentandroidnetworking.fragment.FragmentMore
import com.hoangpro.assignmentandroidnetworking.fragment.LatestFragment
import com.hoangpro.assignmentandroidnetworking.model.EventMessage
import com.hoangpro.assignmentandroidnetworking.util.AnimationHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs

class MainActivity : BaseActivity() {
    private lateinit var categoryFragment: CategoryFragment
    private lateinit var fragmentMore: FragmentMore
    private lateinit var latestFragment: LatestFragment
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var handleDownLoadDialog: HandleDownLoadDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(metrics)
        preferenceHelper.setSize(metrics.widthPixels, metrics.heightPixels)
    }

    private fun setupView() {
        handleDownLoadDialog = HandleDownLoadDialog(this)
        categoryFragment = CategoryFragment()
        fragmentMore = FragmentMore()
        latestFragment = LatestFragment()
        viewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        viewPagerAdapter.apply {
            addFragment(latestFragment)
            addFragment(categoryFragment)
            addFragment(fragmentMore)
        }
        viewPager.adapter = viewPagerAdapter
        viewPager.setPageTransformer(false) { view, position ->
            val scaleValue = abs(abs(position) - 1)
            view.scaleX = scaleValue / 2 + 0.5f
            view.scaleY = scaleValue / 2 + 0.5f
            view.alpha = scaleValue / 2 + 0.5f
            view.rotationY = position * -30
        }
        bubbleTabBar.setupBubbleTabBar(viewPager)
        bubbleTabBar.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                when (id) {
                    R.id.item_home -> viewPager.setCurrentItem(0, false)
                    R.id.item_categories -> viewPager.setCurrentItem(1, false)
                    R.id.item_more -> viewPager.setCurrentItem(2, false)
                }
            }
        })
        viewPager.offscreenPageLimit = 3

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (val fragment = viewPagerAdapter.fragmentList[position]) {
                    is CategoryFragment -> {
                        fragment.initData()
                    }
                }
            }
        })
    }

    override fun onEventBus(eventMessage: EventMessage) {
        when (eventMessage.state) {
            EventMessage.EventState.EVENT_UPDATE_PROGRESS_DOWNLOAD -> {
                handleDownLoadDialog.activeProgress(eventMessage.any as Float)
            }
        }
    }


    class HandleDownLoadDialog(context: Context) {
        private var pgDownload: ProgressBar
        private var tvPgDownload: TextView
        private var tvResult: TextView
        private var tvDone: TextView
        private var downloadDialog: AlertDialog


        init {
            val builder = AlertDialog.Builder(context)
            val downloadDialogView =
                LayoutInflater.from(context).inflate(R.layout.dialog_loading, null, false)
            pgDownload = downloadDialogView.findViewById(R.id.pgDownload)
            pgDownload.max = 100
            tvPgDownload = downloadDialogView.findViewById(R.id.tvPgDownload)
            tvResult = downloadDialogView.findViewById(R.id.tvResult)
            tvDone = downloadDialogView.findViewById(R.id.tvDone)
            builder.setView(downloadDialogView)
            downloadDialog = builder.create()
            downloadDialog.setCancelable(false)
            AnimationHelper.scaleClickAnimation(tvDone) {
                downloadDialog.dismiss()
            }
        }

        fun activeProgress(progress: Float) {
            if (!downloadDialog.isShowing) {
                downloadDialog.show()
            }
            if (progress < 100f) {
                pgDownload.visibility = View.VISIBLE
                tvPgDownload.visibility = View.VISIBLE
                tvResult.visibility = View.GONE
                tvDone.visibility = View.GONE
                pgDownload.progress = progress.toInt()
                tvPgDownload.text = "${String.format("%.2f", progress)}%"
            } else {
                pgDownload.visibility = View.GONE
                tvPgDownload.visibility = View.GONE
                tvResult.visibility = View.VISIBLE
                tvDone.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        if (!categoryFragment.onBackPressed())
            super.onBackPressed()
    }
}