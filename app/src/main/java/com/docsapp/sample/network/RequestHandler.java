package com.docsapp.sample.network;

import android.util.Log;

import com.docsapp.sample.Factory;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;

public class RequestHandler {

    public static String makeRequest(Request request) throws IOException, JSONException {
        return doRequest(request);
    }

    private static String doRequest(Request request) throws IOException, JSONException {
        Log.i("HTTP", request.method() + " : " + request.urlString());
        OkHttpClient httpClient = Factory.getOkHTTPClient();
        Response response = httpClient.newCall(request).execute();
        String body = response.body().string();
        Log.i("HTTP", response.code() + " : " + body);
        if (!response.isSuccessful()) {
            Log.e("Error", "HTTP Error - " + response.message());
        } else {
            return body;
        }
        return null;
    }
}
