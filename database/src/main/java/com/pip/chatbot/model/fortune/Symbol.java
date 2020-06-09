package com.pip.chatbot.model.fortune;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Symbol{
    String symbol;
    String name;
    boolean isCurrency;
}
