package com.example.minh98.downloadimgusingservicebound

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadIMGService : Service() {

    var bitmap:Bitmap?=null

    override fun onBind(intent: Intent): IBinder? {
        return myBinder()
    }

    public class myBinder : Binder() {
        public fun getService():DownloadIMGService{
            return DownloadIMGService()
        }
    }

    fun getBitmap(photoLink: String): Bitmap? {

        Thread(object :Runnable{
            override fun run() {
                val url:URL= URL(photoLink)
                val con:HttpURLConnection= url.openConnection() as HttpURLConnection
                con.connect()
                val ios:InputStream=con.inputStream
                bitmap=BitmapFactory.decodeStream(ios)
                ios.close()
            }

        }).start()

        while(bitmap==null){
            //wait 1s
            SystemClock.sleep(1000)
        }
        return bitmap
    }
}
