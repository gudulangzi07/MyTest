package com.mytest.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.baidu.ocr.sdk.OCR
import com.baidu.ocr.sdk.OnResultListener
import com.baidu.ocr.sdk.exception.OCRError
import com.baidu.ocr.sdk.model.AccessToken
import com.baidu.ocr.sdk.model.GeneralBasicParams
import com.baidu.ocr.sdk.model.GeneralParams
import com.baidu.ocr.sdk.model.GeneralResult
import com.mytest.R
import com.mytest.utils.FileUtil
import kotlinx.android.synthetic.main.activity_ocr.*
import java.io.File
import java.io.FileInputStream

class OcrActivity: AppCompatActivity() {
    private var hasGotToken = false

    private var alertDialog: AlertDialog.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ocr)

        alertDialog = AlertDialog.Builder(this)

        // 请选择您的初始化方式
        //initAccessToken()
        initAccessTokenWithAkSk()

        btn_orc.setOnClickListener {
            if(checkTokenStatus()){
                recWebimage(this, "${Environment.getExternalStorageDirectory()}/app/image/pic.jpg")
            }
        }

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                //没有权限则申请权限
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1);
            }else {
                //有权限直接执行,docode()不用做处理
                copyAssetsToSDCard()
            }
        }else {
            //小于6.0，不用申请权限，直接执行
            copyAssetsToSDCard()
        }
    }

    /**
     * 以license文件方式初始化
     */
    private fun initAccessToken() {
        OCR.getInstance(this).initAccessToken(object : OnResultListener<AccessToken> {
            override fun onResult(accessToken: AccessToken) {
                val token = accessToken.accessToken
                hasGotToken = true
                alertText("licence方式获取token成功", token)
            }

            override fun onError(error: OCRError) {
                error.printStackTrace()
                error.message?.let { alertText("licence方式获取token失败", it) }
            }
        }, applicationContext)
    }

    /**
     * 用明文ak，sk初始化
     */
    private fun initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(object : OnResultListener<AccessToken> {
            override fun onResult(result: AccessToken) {
                val token = result.accessToken
                hasGotToken = true
                alertText("licence方式获取token成功", token)
            }

            override fun onError(error: OCRError) {
                error.printStackTrace()
                alertText("AK，SK方式获取token失败", error.message!!)
            }
        }, applicationContext, "GLHYkewovlNZelN7vxjMl955", "m6hXvW9WyXVZtwaIfU0VATv7hKnBHmwZ")
    }

    private fun alertText(title: String, message: String) {
        runOnUiThread {
            alertDialog?.setTitle(title)
                    ?.setMessage(message)
                    ?.setPositiveButton("确定", null)
                    ?.show()
        }
    }

    private fun checkTokenStatus(): Boolean {
        if (!hasGotToken) {
            Toast.makeText(applicationContext, "token还未成功获取", Toast.LENGTH_LONG).show()
        }
        return hasGotToken
    }

    fun recWebimage(ctx: Context?, filePath: String?) {
        val param: GeneralParams = GeneralParams()
        param.setDetectDirection(true)
        param.setVertexesLocation(true);
        param.setRecognizeGranularity(GeneralParams.GRANULARITY_SMALL);
        param.imageFile = File(filePath)
        OCR.getInstance(ctx).recognizeAccurateBasic(param, object : OnResultListener<GeneralResult> {
            override fun onResult(result: GeneralResult) {
                val sb = StringBuilder()
                for (wordSimple in result.wordList) {
                    sb.append(wordSimple.words)
                    sb.append("\n")
                }

                tv_txt.text = sb
            }

            override fun onError(error: OCRError) {

            }
        })
    }

    private fun copyAssetsToSDCard(){
        FileUtil(applicationContext).copyAssetsToSD("image", "app/image")?.setFileOperateCallback(object : FileUtil.FileOperateCallback {
            override fun onSuccess() {
                val fis = FileInputStream("${Environment.getExternalStorageDirectory()}/app/image/pic.jpg")
                iv_image.setImageBitmap(BitmapFactory.decodeStream(fis))
            }

            override fun onFailed(error: String?) {}
        })
    }
}