package com.mytest.widget;

import android.animation.TypeEvaluator;

public class CustomPointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        CustomPoint startPoint = (CustomPoint) startValue;
        CustomPoint endPoint = (CustomPoint) endValue;
        float x = startPoint.getPointX() + fraction * (endPoint.getPointX() - startPoint.getPointX());
        float y = startPoint.getPointY() + fraction * (endPoint.getPointY() - startPoint.getPointY());
        CustomPoint point = new CustomPoint(x, y);
        return point;
    }
}
