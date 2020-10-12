package com.mytest.utils

import android.content.Context
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.Message
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class FileUtil(private var context: Context?) {
    private var instance: FileUtil? = null
    private val SUCCESS = 1
    private val FAILED = 0
    private var callback: FileOperateCallback? = null

    @Volatile
    private var isSuccess = false
    private var errorStr: String? = null

    fun getInstance(context: Context): FileUtil? {
        if (instance == null) instance = FileUtil(context)
        return instance
    }

    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (callback != null) {
                if (msg.what == SUCCESS) {
                    callback!!.onSuccess()
                }
                if (msg.what == FAILED) {
                    callback!!.onFailed(msg.obj.toString())
                }
            }
        }
    }

    fun copyAssetsToSD(srcPath: String, sdPath: String): FileUtil? {
        Thread {
            copyAssetsToDst(context, srcPath, sdPath)
            if (isSuccess) handler.obtainMessage(SUCCESS).sendToTarget() else handler.obtainMessage(FAILED, errorStr).sendToTarget()
        }.start()
        return this
    }

    fun setFileOperateCallback(callback: FileOperateCallback?) {
        this.callback = callback
    }

    private fun copyAssetsToDst(context: Context?, srcPath: String, dstPath: String) {
        try {
            val fileNames: Array<String> = context?.getAssets()?.list(srcPath) as Array<String>
            if (fileNames.size > 0) {
                val file = File(Environment.getExternalStorageDirectory(), dstPath)
                if (!file.exists()) file.mkdirs()
                for (fileName in fileNames) {
                    if (srcPath != "") { // assets 文件夹下的目录
                        copyAssetsToDst(context, srcPath + File.separator.toString() + fileName, dstPath + File.separator.toString() + fileName)
                    } else { // assets 文件夹
                        copyAssetsToDst(context, fileName, dstPath + File.separator.toString() + fileName)
                    }
                }
            } else {
                val outFile = File(Environment.getExternalStorageDirectory(), dstPath)
                val `is`: InputStream = context.getAssets().open(srcPath)
                val fos = FileOutputStream(outFile)
                val buffer = ByteArray(1024)
                var byteCount: Int
                while (`is`.read(buffer).also { byteCount = it } != -1) {
                    fos.write(buffer, 0, byteCount)
                }
                fos.flush()
                `is`.close()
                fos.close()
            }
            isSuccess = true
        } catch (e: Exception) {
            e.printStackTrace()
            errorStr = e.message
            isSuccess = false
        }
    }

    interface FileOperateCallback {
        fun onSuccess()
        fun onFailed(error: String?)
    }
}