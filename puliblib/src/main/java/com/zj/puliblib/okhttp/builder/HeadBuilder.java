package com.zj.puliblib.okhttp.builder;


import com.zj.puliblib.okhttp.OkHttpUtils;
import com.zj.puliblib.okhttp.request.OtherRequest;
import com.zj.puliblib.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
