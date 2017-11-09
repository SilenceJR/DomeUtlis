package com.silence.materialdesigndome;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @作者: PJ
 * @创建时间: 2017/10/25 / 14:15
 * @描述: 这是一个 MyTestListAdapter 类.
 */
public class MyTestListAdapter extends RecyclerView.Adapter<MyTestListAdapter.myViewHolder> {

    private List<String> datas;

    public MyTestListAdapter(List<String> datas) {
        this.datas = datas;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_test_list, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {
        holder.mTv.setText(datas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItenClick(datas.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        public myViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    interface onItemClickListener {
        void onItenClick(String TvContent);
    }

    private onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
