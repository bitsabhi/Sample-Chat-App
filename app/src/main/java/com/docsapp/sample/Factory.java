package com.docsapp.sample;


import com.docsapp.sample.services.SendMessageService;
import com.squareup.okhttp.OkHttpClient;

import net.jcip.annotations.GuardedBy;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

public class Factory {
    private static final Object LOCK = new Object();
    public static final int TIMEOUT_IN_MS = 30000;
    @GuardedBy("LOCK")
    private static OkHttpClient mOkHttpClient;
    @GuardedBy("LOCK")
    private static SendMessageService sSendMessageService;
    private static CookieManager mCookieManager;


    public static OkHttpClient getOkHTTPClient() {
        synchronized (LOCK) {
            if (mOkHttpClient == null) {
                mOkHttpClient = new OkHttpClient();
                mOkHttpClient.setConnectTimeout(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS);
                mOkHttpClient.setReadTimeout(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS);
                setCookieHandler();
            }
        }
        return mOkHttpClient;
    }

    private static void setCookieHandler() {
        synchronized (LOCK) {
            mCookieManager = new CookieManager();
            mCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(mCookieManager);
            mOkHttpClient.setCookieHandler(mCookieManager);
        }
    }

    public static SendMessageService sendMessageService() {
        synchronized (LOCK) {
            if (sSendMessageService == null) {
                sSendMessageService = new SendMessageService();
            }
        }
        return sSendMessageService;
    }

}
