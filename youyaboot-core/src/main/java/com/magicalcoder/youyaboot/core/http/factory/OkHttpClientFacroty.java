package com.magicalcoder.youyaboot.core.http.factory;

import com.magicalcoder.youyaboot.core.http.constant.HttpClientConstant;
import com.magicalcoder.youyaboot.core.http.utils.HttpClientGet;
import com.magicalcoder.youyaboot.core.http.utils.HttpClientMultipartPost;
import com.magicalcoder.youyaboot.core.http.utils.HttpClientPost;
import com.magicalcoder.youyaboot.core.http.utils.IHttpClient;

/**
 * Created by hzhedongyu on 2015/10/14.
 * 构造基础请求类
 * httpclient 会出现死锁问题 抛弃使用 改OKHttp
 */
public class OkHttpClientFacroty {

    public static IHttpClient create(String method){
        String lowerMethod = method.toLowerCase();
        if(HttpClientConstant.POST.equals(lowerMethod)){
            return HttpClientPost.get();
        }else if(HttpClientConstant.MULTIPART_POST.endsWith(lowerMethod)){
            return HttpClientMultipartPost.get();
        }else{
            return HttpClientGet.get();
        }
    }

    public static IHttpClient createPost(){
        return create(HttpClientConstant.POST);
    }
    public static IHttpClient createGet(){
        return create(HttpClientConstant.GET);
    }
    public static IHttpClient createMultipartPost(){
        return create(HttpClientConstant.MULTIPART_POST);
    }
}
