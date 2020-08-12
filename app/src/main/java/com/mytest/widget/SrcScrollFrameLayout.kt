package com.mytest.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import com.mytest.R

class SrcScrollFrameLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object{
        //重绘的时间间隔
        private const val DEFAULT_DRAW_INTERVALS_TIME = 5L
    }

    //间隔时间内平移距离
    private var mPanDistance = 0f;

    //间隔时间内平移增距
    private var mIntervalIncreaseDistance = 0.5f

    //填满当前view所需bitmap个数
    private var mBitmapCount = 0

    //是否开始滚动
    private var mIsScroll = false;

    //遮罩层颜色
    @ColorInt
    private var mMaskLayerColor = Color.TRANSPARENT;

    private var mDrawable: Drawable? = null
    private var mSrcBitmap: Bitmap? = null
    private var mPaint: Paint? = null
    private var mMatrix: Matrix? = null

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.SrcScrollFrameLayout, defStyleAttr, 0)

        val speed = array.getInteger(R.styleable.SrcScrollFrameLayout_srcSpeed, 3)
        mIntervalIncreaseDistance *= speed
        mDrawable = array.getDrawable(R.styleable.SrcScrollFrameLayout_srcDrawable)
        mIsScroll = array.getBoolean(R.styleable.SrcScrollFrameLayout_srcIsScroll, true)
        mMaskLayerColor = array.getColor(R.styleable.SrcScrollFrameLayout_srcMaskLayoutColor, Color.TRANSPARENT)

        array.recycle()
        setWillNotDraw(false)
        mPaint = Paint()
        mMatrix = Matrix()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mDrawable == null || mDrawable !is BitmapDrawable){
            return
        }
        if (visibility == View.GONE){
            return
        }
        if (w == 0 || h == 0){
            return
        }
        if (mSrcBitmap == null){
            val bitmap = (mDrawable as BitmapDrawable).bitmap
            //按当前view宽度比例缩放bitmap
            mSrcBitmap = scaleBitmap(bitmap, measuredWidth)
            //计算至少需要几个bitmap才能填满当前view
            mBitmapCount = measuredHeight / mSrcBitmap!!.height + 1
            if (!bitmap.isRecycled){
                bitmap.isRecycled
                System.gc()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mSrcBitmap == null){
            return
        }
        if (mSrcBitmap!!.height + mPanDistance != 0f){
            //第一张图片未完全滚出屏幕
            mMatrix?.reset()
            mMatrix?.postTranslate(0f, mPanDistance)
            mMatrix?.let { canvas?.drawBitmap(mSrcBitmap!!, it, mPaint) }
        }

        if (mSrcBitmap!!.height + mPanDistance < measuredHeight){
            //用于补充留白的图片出现在屏幕
            for (index in 0..mBitmapCount){
                mMatrix?.reset()
                mMatrix?.postTranslate(0f, (index + 1) * mSrcBitmap!!.height + mPanDistance)
                mMatrix?.let { canvas?.drawBitmap(mSrcBitmap!!, it, mPaint) }
            }
        }

        //绘制遮罩层
        if (mMaskLayerColor != Color.TRANSPARENT){
            canvas?.drawColor(mMaskLayerColor)
        }

        //延时绘制实现滚动效果
        if (mIsScroll){
            handler.postDelayed(mRedrawRunnable, DEFAULT_DRAW_INTERVALS_TIME)
        }
    }

    /**
     * 重绘
     * */
    private val mRedrawRunnable = Runnable {
        if (mSrcBitmap!!.height + mPanDistance <= 0){
            //第一张已经完全滚出屏幕，重置平移距离
            mPanDistance = 0f
        }
        mPanDistance -=mIntervalIncreaseDistance
        invalidate()
    }

    /**
     * 开始滚动
     * */
    fun startScroll(){
        if (mIsScroll){
            return
        }
        mIsScroll = true
        handler.postDelayed(mRedrawRunnable, DEFAULT_DRAW_INTERVALS_TIME)
    }

    /**
     * 停止滚动
     * */
    fun stopScroll(){
        if (!mIsScroll){
            return
        }
        mIsScroll = false
        handler.removeCallbacks(mRedrawRunnable)
    }

    /**
     * 设置背景图bitmap
     * */
    fun setSrcBitmap(srcBitmap: Bitmap){
        val ordScrollStatus = mIsScroll
        if (ordScrollStatus){
            stopScroll()
        }
        //按当前view的宽度缩放bitmap
        mSrcBitmap = scaleBitmap(srcBitmap, measuredWidth)
        //计算至少需要几个bitmap才能填满当前view
        mBitmapCount = measuredHeight / mSrcBitmap!!.height + 1
        if (!srcBitmap.isRecycled){
            srcBitmap.isRecycled
            System.gc()
        }
        if (ordScrollStatus){
            startScroll()
        }
    }

    /**
     * 缩放bitmap
     * */
    private fun scaleBitmap(originBitmap: Bitmap, newWidth: Int): Bitmap{
        val width = originBitmap.width
        val height = originBitmap.height
        val scaleWidth = newWidth.toFloat() / width
        val newHeight = scaleWidth * height
        val scaleHeight = newHeight / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(originBitmap, 0, 0, width, height, matrix, true)
    }
}