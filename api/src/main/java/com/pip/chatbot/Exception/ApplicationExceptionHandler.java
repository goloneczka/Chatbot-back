package com.pip.chatbot.Exception;

import com.pip.chatbot.payload.response.ErrorResponse;
import com.pip.chatbot.payload.response.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(ChatbotException.class)
    public ResponseEntity<ErrorResponse> handleChatbotException(ChatbotException e, WebRequest webRequest) {
        return ResponseEntity
                .status(ResponseStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getErrors()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherException(Exception e, WebRequest webRequest) {
        return ResponseEntity
                .status(ResponseStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(List.of(e.getMessage())));
    }
}
