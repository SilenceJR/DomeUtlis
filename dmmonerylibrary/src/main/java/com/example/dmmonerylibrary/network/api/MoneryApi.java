package com.example.dmmonerylibrary.network.api;

import com.example.dmmonerylibrary.MoneryRespMsg;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @作者: PJ
 * @创建时间: 2017/11/23 / 15:03
 * @描述: 这是一个 MoneryApi 类.
 */
public interface MoneryApi {

    /**
     * 获取钱包余额
     * @param loginName 登陆用户
     * @return
     */
    @POST(PayConstant.DUIMY_MONEY_QUERY_DMBAO_BALANCE)
    Observable<MoneryRespMsg<String>> queryDmBaoBalance(@Query("loginName") String loginName);

    /**
     * 查询明细
     * @param loginName 登陆用户
     * @param pageNum 查询页数
     * @return
     */
    @POST(PayConstant.DUIMY_MONEY_QUERY_RECHARGE_ORDER)
    Observable<MoneryRespMsg<String>> queryRechargeOrder(@Query("loginName") String loginName, @Query("pageNum") int pageNum);


}
