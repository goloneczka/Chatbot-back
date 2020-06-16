/*
 * This file is generated by jOOQ.
 */
package com.pip.chatbot.jooq.fortune.tables;


import com.pip.chatbot.jooq.fortune.Fortune;
import com.pip.chatbot.jooq.fortune.Keys;
import com.pip.chatbot.jooq.fortune.tables.records.CurrencyRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row1;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Currency extends TableImpl<CurrencyRecord> {

    private static final long serialVersionUID = 448110019;

    /**
     * The reference instance of <code>fortune.currency</code>
     */
    public static final Currency CURRENCY = new Currency();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CurrencyRecord> getRecordType() {
        return CurrencyRecord.class;
    }

    /**
     * The column <code>fortune.currency.name</code>.
     */
    public final TableField<CurrencyRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>fortune.currency</code> table reference
     */
    public Currency() {
        this(DSL.name("currency"), null);
    }

    /**
     * Create an aliased <code>fortune.currency</code> table reference
     */
    public Currency(String alias) {
        this(DSL.name(alias), CURRENCY);
    }

    /**
     * Create an aliased <code>fortune.currency</code> table reference
     */
    public Currency(Name alias) {
        this(alias, CURRENCY);
    }

    private Currency(Name alias, Table<CurrencyRecord> aliased) {
        this(alias, aliased, null);
    }

    private Currency(Name alias, Table<CurrencyRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Currency(Table<O> child, ForeignKey<O, CurrencyRecord> key) {
        super(child, key, CURRENCY);
    }

    @Override
    public Schema getSchema() {
        return Fortune.FORTUNE;
    }

    @Override
    public UniqueKey<CurrencyRecord> getPrimaryKey() {
        return Keys.CURRENCY_PKEY;
    }

    @Override
    public List<UniqueKey<CurrencyRecord>> getKeys() {
        return Arrays.<UniqueKey<CurrencyRecord>>asList(Keys.CURRENCY_PKEY);
    }

    @Override
    public Currency as(String alias) {
        return new Currency(DSL.name(alias), this);
    }

    @Override
    public Currency as(Name alias) {
        return new Currency(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Currency rename(String name) {
        return new Currency(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Currency rename(Name name) {
        return new Currency(name, null);
    }

    // -------------------------------------------------------------------------
    // Row1 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row1<String> fieldsRow() {
        return (Row1) super.fieldsRow();
    }
}
