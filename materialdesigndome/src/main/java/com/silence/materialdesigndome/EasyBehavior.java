package com.silence.materialdesigndome;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @作者: PJ
 * @创建时间: 2017/10/27 / 15:15
 * @描述: 这是一个 EasyBehavior 类.
 */
public class EasyBehavior extends CoordinatorLayout.Behavior<TextView> {

    public EasyBehavior() {
    }

    public EasyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        //告知监听的dependency是Button
        return dependency instanceof Button;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        child.setX(dependency.getX()+200);
        child.setY(dependency.getY()+200);
        child.setText(dependency.getX()+", " + dependency.getY());
        return true;
    }
}
