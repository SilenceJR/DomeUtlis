package com.example.dmmonerylibrary.network.api;

/**
 * @作者: PJ
 * @创建时间: 2017/11/23 / 15:59
 * @描述: 这是一个 PayConstant 类.
 */
class PayConstant {

    public static final String DUIMY_UPLOAD = "http://39.108.88.1/";
    //    public static final String DUIMY_UPLOAD = "http://192.168.1.139/";
    //    public static final String DUIMY_UPLOAD = "http://192.168.1.186:8080/";
    //    public static final String DUIMY_UPLOAD = "http://192.168.1.40/";
    public static final String DUIMY_APP_HOST = "duimy/app/";
    public static final String DUIMY_HOST = DUIMY_UPLOAD + DUIMY_APP_HOST;
    public static final String DUIMY_MONEY_HOST = DUIMY_APP_HOST + "money/";


    /**
     * 获取钱包余额
     * 参数 (String loginName)
     */
    public static final String DUIMY_MONEY_QUERY_DMBAO_BALANCE = DUIMY_MONEY_HOST + "queryDmBaoBalance";

    /**
     * 查询明细
     * 参数 (String loginName)
     * 参数 (int pageNum)
     */
    public static final String DUIMY_MONEY_QUERY_RECHARGE_ORDER = DUIMY_MONEY_HOST + "queryRechargeOrder";

}
