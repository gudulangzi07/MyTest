package com.mytest.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mytest.R
import kotlinx.android.synthetic.main.activity_web_view.*


class WebViewActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val webSetting = webView.settings
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSetting.setSupportZoom(false)
        webSetting.displayZoomControls = false
        webSetting.builtInZoomControls = false
        webSetting.builtInZoomControls = true
        webSetting.useWideViewPort = true
        webSetting.loadWithOverviewMode = true
        webSetting.setSupportMultipleWindows(false)
        webSetting.setAppCacheEnabled(true)
        webSetting.databaseEnabled = true
        webSetting.domStorageEnabled = true
        webSetting.javaScriptEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        //根据cache-control决定是否从网络上取数据
        webSetting.cacheMode = WebSettings.LOAD_DEFAULT

        webView.webViewClient = object : WebViewClient() {
            /**
             * 防止加载网页时调起系统浏览器
             */
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        webView.webChromeClient = WebChromeClient()

        webView.setDownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        webView.setOnLongClickListener {
            val res = webView.hitTestResult
            if (res.type == WebView.HitTestResult.IMAGE_TYPE ||
                    res.type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse(res.extra)
                startActivity(intent)
            }
            true
        }

        webView.loadUrl("https://szsl-mobile-qa.ajmd-med.com/oauth/login?username=A003&accessToken=0959566A64F78A7084991E52D4DBC82B")
    }
}