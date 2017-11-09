package com.silence.duimymoneylibrary;

import com.silence.duimymoneylibrary.inter.onCallNetWorkInterface;
import com.silence.duimymoneylibrary.model.RespMsg;
import com.silence.duimymoneylibrary.network.DuimyMoneyManage;
import com.silence.duimymoneylibrary.network.api.DuimyMoneyApi;

/**
 * @作者: PJ
 * @创建时间: 2017/11/1 / 17:40
 * @描述: 这是一个 DuimyMoney 类.
 */
public class DuimyMoney {

    private static DuimyMoney mDuimyMoney;
    private static DuimyMoneyManage mDuimyMoneyManage;
    private static DuimyMoneyApi mDuimyMoneyApi;


    public static DuimyMoney getInstace() {
        if (mDuimyMoney == null) {
            synchronized (DuimyMoney.class) {
                if (mDuimyMoney == null) {
                    mDuimyMoney = new DuimyMoney();
                }
            }
        }
        if (mDuimyMoneyManage == null) {
            synchronized (DuimyMoney.class) {
                if (mDuimyMoneyManage == null) {
                    mDuimyMoneyManage = new DuimyMoneyManage();
                }
            }
        }
        if (mDuimyMoneyApi == null) {
            synchronized (DuimyMoney.class) {
                if (mDuimyMoneyApi == null) {
                    mDuimyMoneyApi = DuimyMoneyManage.getDuimyMoneyApi();
                }
            }
        }
        return mDuimyMoney;
    }

    public void getUserMoney(String loginName, onCallNetWorkInterface<RespMsg> onCallNetWorkInterface) {
        mDuimyMoneyManage.load(mDuimyMoneyApi.getMoney(loginName), onCallNetWorkInterface);
    }




}
