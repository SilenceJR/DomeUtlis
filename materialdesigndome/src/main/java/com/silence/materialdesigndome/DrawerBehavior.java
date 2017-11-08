package com.silence.materialdesigndome;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * @作者: PJ
 * @创建时间: 2017/10/27 / 15:38
 * @描述: 这是一个 DrawerBehavior 类.
 */
public class DrawerBehavior extends CoordinatorLayout.Behavior<TextView> {

    private int mStartY;

    public DrawerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        if (mStartY == 0) {
            mStartY = (int) dependency.getY();
        }

        float percent = dependency.getY() / mStartY;

        child.setY(child.getHeight() * (1 - percent) - child.getHeight());

        return true;
    }
}
