package com.example.gankiouilife;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @作者: PJ
 * @创建时间: 2017/11/16 / 16:17
 * @描述: 这是一个 MainAdapter 类.
 */
public class MainAdapter extends BaseQuickAdapter<AndroidDateModel, BaseViewHolder> {

    public MainAdapter(@Nullable List<AndroidDateModel> data) {
        super(R.layout.item_main, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AndroidDateModel item) {
        helper.setText(R.id.item_tile, item.getDesc())
                .setText(R.id.item_numbel, helper.getLayoutPosition() + "")
        ;

        ImageView imageView = (ImageView) helper.getView(R.id.item_iv);
        List<String> images = item.getImages();



        if (images != null) {
            Glide.with(mContext)
                    .load(images.get(0))
                    .asBitmap()
                    .into(imageView);
        }

    }
}
