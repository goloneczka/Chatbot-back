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
    private Long id;
    private String symbol;
    private float value;
    private LocalDateTime date;
    private boolean isHistorical;
}
