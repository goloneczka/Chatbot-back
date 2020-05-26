package com.pip.chatbot.model.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    Integer id;
    Symbol symbol;
    float value;
    private LocalDateTime date;
    boolean isHistorical;
}
