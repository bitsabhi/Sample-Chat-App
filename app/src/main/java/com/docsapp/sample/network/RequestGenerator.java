package com.docsapp.sample.network;

import android.support.annotation.NonNull;

import com.squareup.okhttp.Request;


public class RequestGenerator {
    public static Request get(@NonNull String url) {
        Request.Builder builder = new Request.Builder().url(url);
        return builder.build();
    }
}
