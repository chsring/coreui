package com.srwing.enterwechat;

import java.util.WeakHashMap;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Description:
 * Created by small small su
 * Date: 2021/12/25
 * Email: surao@foryou56.com
 */
public interface WeChatService {

    @GET("Wechat/outOfSchool")
    Observable<WeChatEntity> getUser(@QueryMap WeakHashMap<String, Object> params);

}
