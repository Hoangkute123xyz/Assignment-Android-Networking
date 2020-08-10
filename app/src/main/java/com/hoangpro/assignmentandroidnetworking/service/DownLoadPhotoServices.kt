package com.hoangpro.assignmentandroidnetworking.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.hoangpro.assignmentandroidnetworking.R
import com.hoangpro.assignmentandroidnetworking.model.EventMessage
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.random.Random

class DownLoadPhotoServices : Service() {
    companion object {
        val CHANNEL_ID = "DownLoadPhotoServices"
        val URL_PARAM_NAME = "url"
    }
    private var NOTIFICATION_ID=0
    private lateinit var remoteViews: RemoteViews
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var scope: CoroutineScope
    var url = ""
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        NOTIFICATION_ID = Random(1000000).nextInt()
        scope = CoroutineScope(Dispatchers.Default)
        remoteViews = createNotificationLayout()
        url = intent!!.getStringExtra(URL_PARAM_NAME)!!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotification()
        }
        scope.launch {
            val responseAsync = async {
                val client = OkHttpClient.Builder().build()
                val request = Request.Builder().url(url).get().build()
                return@async client.newCall(request).execute()
            }
            val response = responseAsync.await()
            response.body()!!.byteStream().writeFile((response.header("x-ttdb-l")?:"0").toInt())
            withContext(Dispatchers.Main){
                delay(2000L)
                stopSelf()
            }
        }
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        val notificationChannel =
            NotificationChannel(CHANNEL_ID, "Download Service", NotificationManager.IMPORTANCE_LOW)
        notificationManager = getSystemService(NotificationManager::class.java)!!
        notificationManager.createNotificationChannel(notificationChannel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification() {
        createNotificationChannel()
        notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setCustomContentView(remoteViews)
            .setSmallIcon(R.drawable.ic_baseline_cloud_download_24_oragne)
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private suspend fun InputStream.writeFile(maxSize:Int) = use { input ->
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        if (!dir.exists())
            dir.mkdir()
        val file = File(dir.absolutePath, "IMG_${System.currentTimeMillis()}.jpg")
        val output = FileOutputStream(file)
        output.use { output ->
            val buffer = ByteArray(1024*4)
            var read = input.read(buffer)
            var data=read
            while (read != -1) {
                output.write(buffer, 0, read)
                val percent=(data.toFloat()/maxSize.toFloat())*100f
                if (percent<=100f){
                    withContext(Dispatchers.Main){
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) return@withContext
                        EventBus.getDefault().post(EventMessage(percent,EventMessage.EventState.EVENT_UPDATE_PROGRESS_DOWNLOAD))
                        remoteViews.setProgressBar(R.id.pgDownload,100,percent.toInt(),false)
                        remoteViews.setTextViewText(R.id.tvPgDownload,"${String.format("%.2f",percent)}%")
                        notificationManager.notify(NOTIFICATION_ID,notificationBuilder.build())
                        delay(100)
                    }
                }
                data+=read
                read = input.read(buffer)
            }
            withContext(Dispatchers.Main){
                delay(200)
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) return@withContext
                EventBus.getDefault().post(EventMessage(100f,EventMessage.EventState.EVENT_UPDATE_PROGRESS_DOWNLOAD))
                remoteViews.setTextViewText(R.id.tvTitle,"Tải xuống hoàn tất :D")
                remoteViews.setTextViewText(R.id.tvPgDownload,"100%")
                remoteViews.setProgressBar(R.id.pgDownload,100,100,false)
                notificationManager.notify(NOTIFICATION_ID,notificationBuilder.build())
            }
            output.flush()
        }
    }

    private fun createNotificationLayout():RemoteViews{
        return RemoteViews(packageName, R.layout.layout_download_notification)
    }
    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        scope.cancel()
    }

}