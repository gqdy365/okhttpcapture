package com.jerome.okhttpcapture;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

@SuppressWarnings("all")
public final class CaptureInfoInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request());
    }
}
