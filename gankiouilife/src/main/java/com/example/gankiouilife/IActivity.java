package com.example.gankiouilife;

/**
 * @作者: PJ
 * @创建时间: 2017/11/16 / 15:21
 * @描述: 这是一个 IActivity 类.
 */
public interface IActivity {

    /**
     * 加载布局资源
     * @return
     */
    int getlayoutId();

    /**
     * 初始化布局
     */
    void initView();

    /**
     * 添加监听
     */
    void initListener();

    /**
     * 初始化数据
     */
    void initData();

}
