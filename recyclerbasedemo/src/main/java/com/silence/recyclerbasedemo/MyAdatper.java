package com.silence.recyclerbasedemo;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @作者: PJ
 * @创建时间: 2017/11/9 / 17:19
 * @描述: 这是一个 MyAdatper 类.
 */
public class MyAdatper extends BaseQuickAdapter<Student, BaseViewHolder> {

    public MyAdatper(@LayoutRes int layoutResId, @Nullable List<Student> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Student item) {
        helper.setText(R.id.item_name, item.getName());
        helper.setText(R.id.item_age, item.getAge() + "");
    }
}
