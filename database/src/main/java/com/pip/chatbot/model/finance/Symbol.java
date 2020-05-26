package com.pip.chatbot.model.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Symbol{
    String symbol;
    String fullName;
    boolean isCurrency;
}
