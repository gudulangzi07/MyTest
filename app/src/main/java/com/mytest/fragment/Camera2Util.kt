package com.mytest.fragment

import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.params.StreamConfigurationMap
import android.util.Size
import android.view.Surface
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.mytest.utils.LogUtil
import java.util.*
import kotlin.collections.ArrayList

class Camera2Util private constructor(){
    private var mActivity: FragmentActivity? = null
    private var mCameraManager: CameraManager? = null

    fun init(activity: FragmentActivity){
        if (mActivity == null){
            mActivity = activity
            mCameraManager = mActivity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager?
        }
    }

    companion object {
        //默认最优的宽度
        const val DEFAULT_WIDTH = 720

        //默认最优的高度
        const val DEFAULT_HEIGHT = 1280

        val instance: Camera2Util by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Camera2Util() }
    }

    /**
     * 获取前置摄像头
     * */
    fun getFrontCameraId(): String?{
        return getCameraId(true)
    }

    /**
     * 获取后置摄像头
     * */
    fun getBackCameraId(): String?{
        return getCameraId(false)
    }

    /**
     * 获取相机Manager
     * */
    fun getCameraManager(): CameraManager?{
        return mCameraManager
    }

    /**
     * 获取相机id
     * */
    private fun getCameraId(useFront: Boolean): String?{
        try {
            mCameraManager?.let {
                val cameraIdList = it.cameraIdList
                if (cameraIdList.isEmpty()) {
                    Toast.makeText(mActivity, "没有可用相机", Toast.LENGTH_SHORT).show()
                    return null
                }
                for (cameraId in cameraIdList) {
                    val characteristics: CameraCharacteristics = it.getCameraCharacteristics(
                        cameraId
                    )
                    val cameraFacing = characteristics.get(CameraCharacteristics.LENS_FACING)
                    if (useFront) {
                        if (cameraFacing == CameraCharacteristics.LENS_FACING_FRONT) {
                            return cameraId
                        }
                    } else {
                        if (cameraFacing == CameraCharacteristics.LENS_FACING_BACK) {
                            return cameraId
                        }
                    }
                }
            }

        }catch (e: CameraAccessException){
            e.printStackTrace()
        }

        return null
    }

    /**
     * 判断camera是否支持新特性
     * */
    fun isSupportLevel(cameraId: String): Boolean{
        val mCameraCharacteristics = mCameraManager?.getCameraCharacteristics(cameraId)
        val supportLevel =
            mCameraCharacteristics?.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
        if (supportLevel == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {
            LogUtil.debug("Camera2Helper", "相机硬件不支持新特性")
            return false
        }

        return true
    }

    /**
     * 获取拍照屏幕的最优预览尺寸
     * */
    fun getCameraOutputSize(cameraId: String): Size{
        val configurationMap = getConfigurationMap(cameraId)
        //保存照片尺寸
        //val savePicSize = configurationMap?.getOutputSizes(ImageFormat.JPEG)
        //相机输出的全部尺寸
        val outSize = configurationMap?.getOutputSizes(SurfaceTexture::class.java)

        val exchange = exchange(cameraId)

        return getBestSize(
            if (exchange) DEFAULT_HEIGHT else DEFAULT_WIDTH,
            if (exchange) DEFAULT_WIDTH else DEFAULT_HEIGHT,
            if (exchange) DEFAULT_HEIGHT else DEFAULT_WIDTH,
            if (exchange) DEFAULT_WIDTH else DEFAULT_HEIGHT,
            outSize?.toList()!!
        )
    }

    /**
     * 获取摄像头支持的所有输出格式和尺寸
     * */
    private fun getConfigurationMap(cameraId: String): StreamConfigurationMap?{
        val mCameraCharacteristics = mCameraManager?.getCameraCharacteristics(cameraId)
        //获取StreamConfigurationMap，它是管理摄像头支持的所有输出格式和尺寸
        return mCameraCharacteristics?.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
    }

    /**
     * 判断是否需要根据屏幕方向与相机方向进行宽高的交换
     * */
    private fun exchange(cameraId: String): Boolean{
        var exchange = false
        val displayRotation = mActivity?.windowManager?.defaultDisplay?.rotation
        //获取摄像头方向
        val mCameraSensorOrientation = getCameraRotation(cameraId)
        when (displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 ->
                if (mCameraSensorOrientation == 90 || mCameraSensorOrientation == 270) {
                    exchange = true
                }
            Surface.ROTATION_90, Surface.ROTATION_270 ->
                if (mCameraSensorOrientation == 0 || mCameraSensorOrientation == 180) {
                    exchange = true
                }
            else -> LogUtil.debug("Camera2Helper", "Display rotation is invalid: $displayRotation")
        }

        LogUtil.debug("Camera2Helper", "屏幕方向  $displayRotation")
        LogUtil.debug("Camera2Helper", "相机方向  $mCameraSensorOrientation")

        return exchange
    }

    /**
     * 获取摄像头方向
     * */
    fun getCameraRotation(cameraId: String): Int{
        val mCameraCharacteristics = mCameraManager?.getCameraCharacteristics(cameraId)
        //获取摄像头方向
        return mCameraCharacteristics?.get(CameraCharacteristics.SENSOR_ORIENTATION) ?: 0
    }

    /**
     *
     * 根据提供的参数值返回与指定宽高相等或最接近的尺寸
     *
     * @param targetWidth   目标宽度
     * @param targetHeight  目标高度
     * @param maxWidth      最大宽度(即TextureView的宽度)
     * @param maxHeight     最大高度(即TextureView的高度)
     * @param sizeList      支持的Size列表
     *
     * @return  返回与指定宽高相等或最接近的尺寸
     *
     */
    private fun getBestSize(
        targetWidth: Int,
        targetHeight: Int,
        maxWidth: Int,
        maxHeight: Int,
        sizeList: List<Size>
    ): Size {
        val bigEnough = ArrayList<Size>()     //比指定宽高大的Size列表
        val notBigEnough = ArrayList<Size>()  //比指定宽高小的Size列表

        for (size in sizeList) {
            //宽<=最大宽度  &&  高<=最大高度  &&  宽高比 == 目标值宽高比
            if (size.width <= maxWidth && size.height <= maxHeight
                && size.width == size.height * targetWidth / targetHeight
            ) {
                if (size.width >= targetWidth && size.height >= targetHeight)
                    bigEnough.add(size)
                else
                    notBigEnough.add(size)
            }
            LogUtil.debug(
                "Camera2Util",
                "系统支持的尺寸: ${size.width} * ${size.height} ,  比例 ：${size.width.toFloat() / size.height}"
            )
        }

        LogUtil.debug(
            "Camera2Util",
            "最大尺寸 ：$maxWidth * $maxHeight, 比例 ：${targetWidth.toFloat() / targetHeight}"
        )
        LogUtil.debug(
            "Camera2Util",
            "目标尺寸 ：$targetWidth * $targetHeight, 比例 ：${targetWidth.toFloat() / targetHeight}"
        )

        //选择bigEnough中最小的值  或 notBigEnough中最大的值
        return when {
            bigEnough.size > 0 -> Collections.min(bigEnough, CompareSizesByArea())
            notBigEnough.size > 0 -> Collections.max(notBigEnough, CompareSizesByArea())
            else -> sizeList[0]
        }
    }

    private class CompareSizesByArea : Comparator<Size> {
        override fun compare(size1: Size, size2: Size): Int {
            return java.lang.Long.signum(size1.width.toLong() * size1.height - size2.width.toLong() * size2.height)
        }
    }

}