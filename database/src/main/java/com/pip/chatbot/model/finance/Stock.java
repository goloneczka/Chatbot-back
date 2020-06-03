package com.pip.chatbot.model.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    private Integer id;
    private Symbol symbol;
    private float value;
    private LocalDate date;
    private boolean isHistorical;
}
