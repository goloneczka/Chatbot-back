package com.pip.chatbot.payload.response;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class Response {
    public final static ResponseEntity<HashMap.SimpleEntry<String,Boolean>> SUCCESS = ResponseEntity
            .status(ResponseStatus.OK)
            .body(new HashMap.SimpleEntry<>("success", true));
    public final static ResponseEntity<?> BAD_REQUEST = ResponseEntity
            .status(ResponseStatus.BAD_REQUEST)
            .body(new HashMap.SimpleEntry<>("message", "Bad request"));
}
