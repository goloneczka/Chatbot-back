package com.pip.chatbot.repository.fortune;

import com.pip.chatbot.jooq.fortune.Tables;
import com.pip.chatbot.model.finance.Symbol;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;

import static com.pip.chatbot.jooq.fortune.tables.Symbol.SYMBOL;

@AllArgsConstructor
public class SymbolRepository {
    private DSLContext dsl;


    public Symbol createSymbol(Symbol symbol) {
        return dsl.insertInto(Tables.SYMBOL)
                .set(SYMBOL.SYMBOL_, symbol.getSymbol())
                .set(SYMBOL.NAME, symbol.getName())
                .set(SYMBOL.IS_CURRENCY, symbol.isCurrency())
                .returning()
                .fetchOne()
                .into(Symbol.class);
    }

    public Optional<Symbol> updateSymbol(Symbol symbol){
        return dsl.update(SYMBOL)
                .set(SYMBOL.NAME, symbol.getName())
                .set(SYMBOL.IS_CURRENCY, symbol.isCurrency())
                .where(SYMBOL.SYMBOL_.eq(symbol.getSymbol()))
                .returning()
                .fetchOptional()
                .map(symbolRecord -> symbolRecord.into(Symbol.class));
    }

    public boolean deleteSymbol(String id){
        return 0 < dsl.delete(SYMBOL)
                .where(SYMBOL.SYMBOL_.eq(id))
                .execute();
    }

    public List<Symbol> getAllCurrencies() {
       return dsl.selectFrom(SYMBOL)
               .where(SYMBOL.IS_CURRENCY.eq(true))
               .fetch()
               .into(Symbol.class);
    }

    public List<Symbol> getAllCompanies() {
        return dsl.selectFrom(SYMBOL)
                .where(SYMBOL.IS_CURRENCY.eq(false))
                .fetch()
                .into(Symbol.class);
    }





}
