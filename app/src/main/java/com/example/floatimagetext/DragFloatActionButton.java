package com.example.floatimagetext;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DragFloatActionButton extends FloatingActionButton {
    private int parentHeight;
    private int parentWidth;

    int firstX;

    public DragFloatActionButton(Context context) {
        super(context);
    }

    public DragFloatActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public DragFloatActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    private int lastX;
    private int lastY;

    private boolean isDrag;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //getRawX是整个屏幕的坐标
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                firstX = (int) event.getX();
                setPressed(true);
                isDrag = false;
                getParent().requestDisallowInterceptTouchEvent(true);
                lastX = rawX;
                lastY = rawY;
                ViewGroup parent;
                if (getParent() != null) {
                    parent = (ViewGroup) getParent();
                    parentHeight = parent.getHeight();
                    parentWidth = parent.getWidth();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (parentHeight <= 0 || parentWidth == 0) {
                    isDrag = false;
                    break;
                } else {
                    isDrag = true;
                }
                int dx = rawX - lastX;
                int dy = rawY - lastY;
                //这里修复一些华为手机无法触发点击事件
                int distance = (int) Math.sqrt(dx * dx + dy * dy);
                if (distance == 0) {
                    isDrag = false;
                    break;
                }
                float x = getX() + dx;
                float y = getY() + dy;
                //检测是否到达边缘 左上右下
                x = x < 0 ? 0 : x > parentWidth - getWidth() ? parentWidth - getWidth() : x;
                y = getY() < 0 ? 0 : getY() + getHeight() > parentHeight ? parentHeight - getHeight() : y;
                setX(x);
                setY(y);
                lastX = rawX;
                lastY = rawY;
                Log.e("aa", "isDrag=" + isDrag + "getX=" + getX() + ";getY=" + getY() + ";parentWidth=" + parentWidth);
                break;
            case MotionEvent.ACTION_UP:
                /*if (!isNotDrag()) {
                    //恢复按压效果
                    setPressed(false);
                    //Log.i("getX="+getX()+"；screenWidthHalf="+screenWidthHalf);
                    if (rawX >= parentWidth / 2) {
                        //靠右吸附
                        animate().setInterpolator(new DecelerateInterpolator())
                                .setDuration(500)
                                .xBy(parentWidth - getWidth() - getX())
                                .start();
                    } else {
                        //靠左吸附
                        ObjectAnimator oa = ObjectAnimator.ofFloat(this, "x", getX(), 0);
                        oa.setInterpolator(new DecelerateInterpolator());
                        oa.setDuration(500);
                        oa.start();
                    }
                }*/
                //在没有位置变化的时候执行点击事件(如果有的话)
                //getX是相对父控件的坐标
                int secondX = (int) event.getX();
                if(firstX == secondX){
                    callOnClick();
                }
                break;
        }
        //如果是拖拽则消s耗事件，否则正常传递即可。
        //return !isNotDrag() || super.onTouchEvent(event);
        return true;
    }

    private boolean isNotDrag() {
        return !isDrag && (getX() == 0
                || (getX() == parentWidth - getWidth()));
    }
}


