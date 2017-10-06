package com.docsapp.sample.models;

import java.util.ArrayList;

public class Message {
    private String chatBotName;

    public String getChatBotName() {
        return this.chatBotName;
    }

    public void setChatBotName(String chatBotName) {
        this.chatBotName = chatBotName;
    }

    private int chatBotID;

    public int getChatBotID() {
        return this.chatBotID;
    }

    public void setChatBotID(int chatBotID) {
        this.chatBotID = chatBotID;
    }

    private String message;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String emotion;

    public String getEmotion() {
        return this.emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }
}

