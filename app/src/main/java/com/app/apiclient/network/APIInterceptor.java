package com.app.apiclient.network;

import android.text.TextUtils;

import com.app.apiclient.utils.PreferenceKeeper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class is used to passing user token at central level.
 */
public class APIInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = PreferenceKeeper.getInstance().getAccessToken();
        Request originalRequest = chain.request();
        if (TextUtils.isEmpty(token)) {
            Request.Builder builder = originalRequest.newBuilder();
            Request oldReq = builder
                    .addHeader("Content-Type:application", "x-www-form-urlencoded")
                    .build();
            return chain.proceed(oldReq);
        }

        Request.Builder builder = originalRequest.newBuilder();
        Request oldReq = builder
                .addHeader("accessToken", token)
                .addHeader("Content-Type:application", "x-www-form-urlencoded")
                .build();
        return chain.proceed(oldReq);
    }
}

