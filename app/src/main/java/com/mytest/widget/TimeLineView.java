package com.mytest.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mytest.R;
import com.mytest.utils.DensityUtil;

public class TimeLineView extends View {

    private int mMarkerSize = 24; //圆点大小,单位为dp
    private int mLineSize = 2; //线段粗细
    private Drawable mBeginLine; //上面线段颜色或者图片
    private Drawable mEndLine; //下面线段颜色或者图片
    private Drawable mMarkerDrawable;//圆点颜色或者图片
    private boolean oritation; //竖向还是横向  false竖向,true横向

    public TimeLineView(Context context) {
        this(context, null);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimeLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TimeLine);
        mMarkerSize = typedArray.getDimensionPixelSize(R.styleable.TimeLine_markerSize, mMarkerSize);
        mLineSize = typedArray.getDimensionPixelSize(R.styleable.TimeLine_lineSize, mLineSize);
        mBeginLine = typedArray.getDrawable(R.styleable.TimeLine_beginLine);
        mEndLine = typedArray.getDrawable(R.styleable.TimeLine_endLine);
        mMarkerDrawable = typedArray.getDrawable(R.styleable.TimeLine_marker);
        oritation = typedArray.getBoolean(R.styleable.TimeLine_oritation, false);

        typedArray.recycle();

        if (mBeginLine != null)
            mBeginLine.setCallback(this);

        if (mEndLine != null)
            mEndLine.setCallback(this);

        if (mMarkerDrawable != null)
            mMarkerDrawable.setCallback(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (oritation) {
            if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(120, 80);//针对wrap情况做处理
            } else if (widthSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(120, heightSpecSize);
            } else if (heightSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(widthSpecSize, 80);
            }
        } else {
            if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(80, 120);//针对wrap情况做处理
            } else if (widthSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(80, heightSpecSize);
            } else if (heightSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(widthSpecSize, 120);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBeginLine != null) {
            mBeginLine.draw(canvas);
        }
        if (mEndLine != null) {
            mEndLine.draw(canvas);
        }
        if (mMarkerDrawable != null) {
            mMarkerDrawable.draw(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initDrawableSize();
    }

    private void initDrawableSize() {
        int pLeft = getPaddingLeft();
        int pRight = getPaddingRight();
        int pTop = getPaddingTop();
        int pBottom = getPaddingBottom();
        int width = getWidth();
        int height = getHeight();
        int cWidth = width - pLeft - pRight;
        int cHeight = height - pTop - pBottom;
        Rect bounds;
        int mMarkerSizepx = DensityUtil.dp2px(getContext(), mMarkerSize);
        int mLineSizepx = DensityUtil.dp2px(getContext(), mLineSize);
        //横向
        if (oritation) {
            if (mMarkerDrawable != null) {
                int marksize = Math.min(Math.min(cWidth, cHeight), mMarkerSizepx);
                mMarkerDrawable.setBounds(pLeft + width / 2 - marksize / 2, pTop, pLeft + width / 2 - marksize / 2 + marksize, pTop + marksize);
                bounds = mMarkerDrawable.getBounds();
            } else {
                bounds = new Rect(pLeft + width / 2, pTop, pLeft + width / 2, pTop);
            }
            int halfLine = mLineSizepx >> 1;
            int linetop = bounds.centerY() - halfLine;
            if (mBeginLine != null) {
                mBeginLine.setBounds(0, linetop, bounds.left, linetop + mLineSizepx);
            }
            if (mEndLine != null) {
                mEndLine.setBounds(bounds.right, linetop, width, linetop + mLineSizepx);
            }
            //竖向
        } else {
            if (mMarkerDrawable != null) {
                int marksize = Math.min(Math.min(cWidth, cHeight), mMarkerSizepx);
                mMarkerDrawable.setBounds(pLeft, pTop + height / 2 - marksize / 2, pLeft + marksize, pTop + height / 2 - marksize / 2 + marksize);
                bounds = mMarkerDrawable.getBounds();
            } else {
                bounds = new Rect(pLeft + mLineSizepx / 2, pTop + height / 2, pLeft + mLineSizepx / 2, pTop + height / 2);
            }
            int halfLine = mLineSizepx >> 1;
            int lineLeft = bounds.centerX() - halfLine;
            if (mBeginLine != null) {
                mBeginLine.setBounds(lineLeft, 0, lineLeft + mLineSizepx, bounds.top);
            }
            if (mEndLine != null) {
                mEndLine.setBounds(lineLeft, bounds.bottom, lineLeft + mLineSizepx, height);
            }
        }
    }

    //下来提供几个方法。以供代码动态设置
    public void setLineSize(int linesize) {
        if (this.mLineSize != linesize) {
            mLineSize = linesize;
            initDrawableSize();
            invalidate();
        }
    }

    public void setMarkerSize(int markerSize) {
        if (this.mMarkerSize != markerSize) {
            mMarkerSize = markerSize;
            initDrawableSize();
            invalidate();
        }
    }

    public void setBeginLine(Drawable beginLine) {
        if (this.mBeginLine != beginLine) {
            this.mBeginLine = beginLine;
            if (mBeginLine != null) {
                mBeginLine.setCallback(this);
            }
            initDrawableSize();
            invalidate();
        }
    }

    public void setEndLine(Drawable endLine) {
        if (this.mEndLine != endLine) {
            this.mEndLine = endLine;
            if (mEndLine != null) {
                mEndLine.setCallback(this);
            }
            initDrawableSize();
            invalidate();
        }
    }

    public void setMarkerDrawable(Drawable markerDrawable) {
        if (this.mMarkerDrawable != markerDrawable) {
            this.mMarkerDrawable = markerDrawable;
            if (mMarkerDrawable != null) {
                mMarkerDrawable.setCallback(this);
            }
            initDrawableSize();
            invalidate();
        }
    }
}
