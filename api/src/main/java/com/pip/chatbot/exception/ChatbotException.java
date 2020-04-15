package com.pip.chatbot.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ChatbotException extends RuntimeException{
    private List<String> errors;
}
