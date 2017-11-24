package com.example.cbcdemo.mvp

/**
 *
 *  @作者:  PJ
 *  @创建时间:    2017/11/23 / 13:59
 *  @描述:  这是一个 Resource 类.
 *
 */
data class ResourceKotlin<T> (var code: String, var data: T?, var errorMsg: String?)