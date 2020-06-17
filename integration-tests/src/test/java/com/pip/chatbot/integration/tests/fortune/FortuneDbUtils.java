package com.pip.chatbot.integration.tests.fortune;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.model.fortune.Stock;
import com.pip.chatbot.model.fortune.Symbol;
import com.pip.chatbot.repository.fortune.StockRepository;
import com.pip.chatbot.repository.fortune.SymbolRepository;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import static com.pip.chatbot.jooq.fortune.tables.Symbol.SYMBOL;
import static com.pip.chatbot.jooq.fortune.tables.Stock.STOCK;

public class FortuneDbUtils {

    private final DSLContext dslContext;
    private final StockRepository stockRepository;
    private final SymbolRepository symbolRepository;

    public FortuneDbUtils(Map<String,String> config) throws SQLException {
        dslContext = DSL.using(DriverManager.getConnection(config.get("url"), config.get("username"), config.get("password")));

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

        this.stockRepository = new StockRepository(dslContext);
        this.symbolRepository = new SymbolRepository(dslContext);
    }

    public void initFortuneData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Symbol symbol = objectMapper.readValue(getClass().getResourceAsStream("/fortune/symbol.json"), Symbol.class);
        Stock stock = objectMapper.readValue(getClass().getResourceAsStream("/fortune/stock.json"), Stock.class);

        stock.setDate(LocalDate.parse("2020-05-04"));
        stock.setId(1L);

        this.symbolRepository.createSymbol(symbol);
        this.stockRepository.createStock(stock);
    }

    public void clearDatabase(){
        dslContext.truncateTable(STOCK).cascade().execute();
        dslContext.truncateTable(SYMBOL).cascade().execute();
    }
}
