package com.pip.chatbot.model.fortune;

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
    private LocalDate date;
    private boolean isHistorical;
}
