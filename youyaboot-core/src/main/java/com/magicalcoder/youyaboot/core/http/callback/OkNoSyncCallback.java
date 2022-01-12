package com.magicalcoder.youyaboot.core.http.callback;


import com.magicalcoder.youyaboot.core.http.dto.ReqReturn;

/**
 * author:hedy
 * date:2019/1/22
 * function:
 */
public interface OkNoSyncCallback {
    //请求成功
    void onResponse(ReqReturn reqReturn);
    //请求失败
    void onFailure();
}
