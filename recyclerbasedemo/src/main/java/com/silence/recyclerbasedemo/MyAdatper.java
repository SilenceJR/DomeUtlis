package com.silence.recyclerbasedemo;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @作者: PJ
 * @创建时间: 2017/11/9 / 17:19
 * @描述: 这是一个 MyAdatper 类.
 */
public class MyAdatper extends BaseMultiItemQuickAdapter<Student, BaseViewHolder> {

    public MyAdatper(@LayoutRes int layoutResId, @Nullable List<Student> data) {
        super(data);
        addItemType(Student.TEXT, R.layout.item_main_text);
        addItemType(Student.IMAGE, R.layout.item_main_image);

    }

    @Override
    protected void convert(BaseViewHolder helper, Student item) {
        switch (helper.getItemViewType()) {
            case Student.TEXT:
                helper.setText(R.id.item_name, item.getName())
                        .setText(R.id.item_age, item.getAge() + "")
                        .addOnClickListener(R.id.item_name)
                        .addOnLongClickListener(R.id.item_age);
                break;
            case Student.IMAGE:
                helper.setImageDrawable(R.id.item_image, item.getDrawable())
                        .addOnClickListener(R.id.item_image);
                break;
            default:
                break;
        }

    }

    @Override
    public int setHeaderView(View header) {
        return super.setHeaderView(header);
    }
}
