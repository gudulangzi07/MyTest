package com.mytest.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mytest.R
import com.mytest.utils.LogUtil
import kotlinx.android.synthetic.main.fragment_camera2.*
import java.io.File
import java.io.FileOutputStream

class Camera2Fragment: Fragment() {

    private var cameraId: String? = null
    private var cameraManager: CameraManager? = null
    private var outputSize: Size? = null
    private lateinit var cameraDevice: CameraDevice
    private lateinit var imageReader: ImageReader
    private lateinit var mCaptureRequestBuilder: CaptureRequest.Builder
    private lateinit var mPreviewRequest: CaptureRequest
    private lateinit var mCaptureSession: CameraCaptureSession

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraId = Camera2Util.instance.getBackCameraId()
        cameraManager = Camera2Util.instance.getCameraManager()
        cameraId?.let {
            outputSize = Camera2Util.instance.getCameraOutputSize(it)
        }
        LogUtil.debug("Camera2Fragment", "${outputSize?.width}---${outputSize?.height}")
        imageReader = ImageReader.newInstance(
            outputSize?.width ?: 0,
            outputSize?.height ?: 0,
            ImageFormat.JPEG,
            1
        )
        imageReader.setOnImageAvailableListener(imageReaderListener, null)

        btnTakePic.setOnClickListener {
            takePhoto()
        }
    }

    override fun onResume() {
        super.onResume()
        if (vTextureView.isAvailable){
            openCamera()
        }else{
            vTextureView.surfaceTextureListener = surfaceTextureListener
        }
    }

    override fun onPause() {
        super.onPause()
        imageReader.close()
        mCaptureSession.close()
        cameraDevice.close()
    }

    private fun openCamera(){
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(requireActivity(), "没有相机权限！", Toast.LENGTH_SHORT).show()
            return
        }

        cameraId?.let {
            cameraManager?.openCamera(it, cameraStateCallback, null)
        }
    }

    //把图片保存到SD卡上
    private fun saveImageToSDCard(){
        val image = imageReader.acquireNextImage()
        val byteBuffer = image.planes[0].buffer
        val byteArray = ByteArray(byteBuffer.remaining())
        byteBuffer.get(byteArray)
        image.close()

        try {
            val file = File("${requireActivity().externalCacheDir}${File.separator}Camera")
            if (!file.exists()) {
                file.mkdirs()
            }

            val path = "${file.absoluteFile}${File.separator}image.jpg"
            val fileOutputStream = FileOutputStream(path)
            fileOutputStream.write(byteArray, 0, byteArray.size)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val surfaceTextureListener = object: TextureView.SurfaceTextureListener{
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            //启动相机
            openCamera()
        }

        override fun onSurfaceTextureSizeChanged(
            surface: SurfaceTexture,
            width: Int,
            height: Int
        ) {

        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            return false
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

        }
    }

    private val cameraStateCallback = object: CameraDevice.StateCallback(){
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera

            try {
                mCaptureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)

                val surface = Surface(vTextureView.surfaceTexture)
                // 将CaptureRequest的构建器与Surface对象绑定在一起
                mCaptureRequestBuilder.addTarget(surface)
                // 闪光灯
                mCaptureRequestBuilder.set(
                    CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
                )
                // 自动对焦
                mCaptureRequestBuilder.set(
                    CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                )

                mPreviewRequest = mCaptureRequestBuilder.build()

                // 为相机预览，创建一个CameraCaptureSession对象
                cameraDevice.createCaptureSession(
                    arrayListOf(surface, imageReader.surface), sessionStateCallback, null
                )
            }catch (e: CameraAccessException){
                e.printStackTrace()
                LogUtil.debug("Camera2Fragment", "相机访问异常")
            }
        }

        override fun onDisconnected(camera: CameraDevice) {
        }

        override fun onError(camera: CameraDevice, error: Int) {
        }
    }

    private val sessionStateCallback = object: CameraCaptureSession.StateCallback(){
        override fun onConfigured(session: CameraCaptureSession) {
            mCaptureSession = session
            try {
                mCaptureSession.setRepeatingRequest(mPreviewRequest, null, null)
            }catch (e: CameraAccessException){
                e.printStackTrace()
                LogUtil.debug("Camera2Fragment", "相机访问异常")
            }
        }

        override fun onConfigureFailed(session: CameraCaptureSession) {
            LogUtil.debug("Camera2Fragment", "会话注册失败")
        }
    }

    private val sessionCaptureCallback = object: CameraCaptureSession.CaptureCallback(){
        override fun onCaptureCompleted(
            session: CameraCaptureSession,
            request: CaptureRequest,
            result: TotalCaptureResult
        ) {
            super.onCaptureCompleted(session, request, result)
            try {
                mCaptureSession.setRepeatingRequest(mPreviewRequest, null, null)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
                Log.d("Camera2Fragment", "相机访问异常")
            }
        }
    }

    private val imageReaderListener = object: ImageReader.OnImageAvailableListener{
        override fun onImageAvailable(reader: ImageReader?) {
           saveImageToSDCard()
        }
    }

    private fun takePhoto(){
        try {
            mCaptureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)

            mCaptureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, Camera2Util.instance.getCameraRotation(cameraId!!))
            mCaptureRequestBuilder.set(
                CaptureRequest.CONTROL_AF_TRIGGER,
                CameraMetadata.CONTROL_AF_TRIGGER_START
            )

            mCaptureRequestBuilder.addTarget(imageReader.surface)

            mPreviewRequest = mCaptureRequestBuilder.build()

            //根据摄像头方向对保存的照片进行旋转，使其为"自然方向"
            mCaptureSession.stopRepeating()
            mCaptureSession.abortCaptures()
            mCaptureSession.capture(mPreviewRequest, sessionCaptureCallback, null)
        }catch (e: CameraAccessException){
            e.printStackTrace()
            Log.d("Camera2Fragment", "相机访问异常")
        }
    }
}