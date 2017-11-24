package com.example.dmuser.inter;

import com.example.dmuser.model.UserVo;

import java.util.List;

/**
 * @作者: PJ
 * @创建时间: 2017/11/24 / 16:54
 * @描述: 这是一个 UserVodbCallBack 类.
 */
public interface UserVodbCallBack {
    void onSuccess(List<UserVo> userVos);
    void onError();
}
