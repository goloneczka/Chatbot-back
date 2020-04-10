package com.pip.chatbot.exception;

import java.util.ArrayList;
import java.util.List;

public class ChatbotExceptionBuilder {
    private List<String> errors;

    public ChatbotExceptionBuilder(){
        this.errors = new ArrayList<>();
    }

    public ChatbotExceptionBuilder addError(String message){
        errors.add(message);
        return this;
    }

    public ChatbotException build(){
        ChatbotException chatbotException = new ChatbotException();
        chatbotException.setErrors(errors);
        return chatbotException;
    }
}
