package com.example.minh98.downloadimgusingservicebound

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    var img: ImageView?=null
    var btn: Button?=null
    var serviceConnect:ServiceConnection?=null
    var isServiceConneted=false
    var myService:DownloadIMGService?=null
    val photoLink="http://anh.24h.com.vn/upload/1-2016/images/2016-03-03/1456995786-con-gai-xinh-dep.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        connectService()
    }

    private fun connectService() {
        serviceConnect=object :ServiceConnection{
            override fun onServiceDisconnected(p0: ComponentName?) {
                isServiceConneted=false
                myService=null
            }

            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {

                val mybinder:DownloadIMGService.myBinder = p1 as DownloadIMGService.myBinder
                myService=mybinder.getService()
                isServiceConneted=true
            }

        }
        bindService(Intent(this,DownloadIMGService::class.java),serviceConnect,BIND_AUTO_CREATE)
    }

    private fun initView() {
        img= findViewById(R.id.imageView) as ImageView?
        btn= findViewById(R.id.button) as Button?
        btn?.setOnClickListener {
            if(isServiceConneted){
                img?.setImageBitmap(myService?.getBitmap(photoLink))
            }
        }
    }

    override fun onDestroy() {
        unbindService(serviceConnect)
        super.onDestroy()
    }
}
