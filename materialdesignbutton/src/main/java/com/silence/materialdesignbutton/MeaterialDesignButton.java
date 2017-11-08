package com.silence.materialdesignbutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import static android.R.attr.radius;

/**
 * @作者: PJ
 * @创建时间: 2017/10/30 / 11:42
 * @描述: 这是一个 MeaterialDesignButton 类.
 */
public class MeaterialDesignButton extends android.support.v7.widget.AppCompatButton {

    public static final String TAG = MeaterialDesignButton.class.getSimpleName();

    /**
     * 按钮被按下时的背景色
     */
    private int mBackColorPress = 0;
    /**
     * 按钮的背景色
     */
    private int mBackColor = 0;
    /**
     * 文字的背景色
     */
    private int mTextColor = 0;
    /**
     * 是否启动按钮圆角
     */
    private boolean mButtonOnRadius = false;
    /**
     * 按钮的圆角
     */
    private float mButtonFloat = 0;


    public MeaterialDesignButton(Context context) {
        super(context, null);
    }

    public MeaterialDesignButton(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MeaterialDesignButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MeaterialDesignButton, defStyleAttr, 0);
        //设置背景色
        if (array != null) {
            ColorStateList colorStateList = array.getColorStateList(R.styleable.MeaterialDesignButton_backColor);
            if (colorStateList != null) {
                mBackColor = colorStateList.getColorForState(getDrawableState(), 0);
                if (mBackColor != 0) {
                    setBackgroundColor(mBackColor);
                }
            }

            ColorStateList colorListPress = array.getColorStateList(R.styleable.MeaterialDesignButton_backColorPress);
            if (colorListPress != null) {
                mBackColorPress = colorListPress.getColorForState(getDrawableState(), 0);
                if (mBackColorPress != 0) {
                    setBackgroundColor(mBackColorPress);
                }
            }

            ColorStateList colorListTextColor = array.getColorStateList(R.styleable.MeaterialDesignButton_textColor);
            if (colorListTextColor != null) {
                mTextColor = colorListTextColor.getColorForState(getDrawableState(), 0);
                if (mTextColor != 0) {
                    setTextColor(mTextColor);
                }
            }

            mButtonOnRadius = array.getBoolean(R.styleable.MeaterialDesignButton_onRadius, false);

            mButtonFloat = array.getFloat(R.styleable.MeaterialDesignButton_radius, 0);

            if (mButtonOnRadius) {
                new GradientDrawable().setCornerRadius(radius);
            }



        }


    }



}
