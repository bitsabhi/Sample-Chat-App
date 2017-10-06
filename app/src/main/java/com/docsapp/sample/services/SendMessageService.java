package com.docsapp.sample.services;

import com.docsapp.sample.concurrency.ExecutorUtils;
import com.docsapp.sample.utils.ApiConstants;
import com.docsapp.sample.network.RequestGenerator;
import com.docsapp.sample.network.RequestHandler;
import com.google.common.util.concurrent.ListenableFuture;
import com.squareup.okhttp.Request;

import java.util.concurrent.Callable;

public class SendMessageService {

    public ListenableFuture<String> sendMessageToChatbot(final String msg) {
        return ExecutorUtils.getBackgroundPool().submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String url = ApiConstants.PERSONALITY_FORGE_API_ENDPOINT
                        +"?"
                        +"apiKey=6nt5d1nJHkqbkphe"
                        +"&chatBotID=63906"
                        +"&externalID=chirag1"
                        +"&message="
                        + msg
                        ;
                Request request = RequestGenerator.get(url);
                String response = RequestHandler.makeRequest(request);
                return response;
            }
        });
    }
}
