/*
 * This file is generated by jOOQ.
 */
package com.pip.chatbot.jooq.food.tables;


import com.pip.chatbot.jooq.food.Food;
import com.pip.chatbot.jooq.food.Keys;
import com.pip.chatbot.jooq.food.tables.records.DishRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
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
public class Dish extends TableImpl<DishRecord> {

    private static final long serialVersionUID = 533346775;

    /**
     * The reference instance of <code>food.dish</code>
     */
    public static final Dish DISH = new Dish();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DishRecord> getRecordType() {
        return DishRecord.class;
    }

    /**
     * The column <code>food.dish.id</code>.
     */
    public final TableField<DishRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('food.dish_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>food.dish.dish</code>.
     */
    public final TableField<DishRecord, String> DISH_ = createField(DSL.name("dish"), org.jooq.impl.SQLDataType.VARCHAR(127).nullable(false), this, "");

    /**
     * The column <code>food.dish.price</code>.
     */
    public final TableField<DishRecord, Float> PRICE = createField(DSL.name("price"), org.jooq.impl.SQLDataType.REAL.nullable(false), this, "");

    /**
     * Create a <code>food.dish</code> table reference
     */
    public Dish() {
        this(DSL.name("dish"), null);
    }

    /**
     * Create an aliased <code>food.dish</code> table reference
     */
    public Dish(String alias) {
        this(DSL.name(alias), DISH);
    }

    /**
     * Create an aliased <code>food.dish</code> table reference
     */
    public Dish(Name alias) {
        this(alias, DISH);
    }

    private Dish(Name alias, Table<DishRecord> aliased) {
        this(alias, aliased, null);
    }

    private Dish(Name alias, Table<DishRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Dish(Table<O> child, ForeignKey<O, DishRecord> key) {
        super(child, key, DISH);
    }

    @Override
    public Schema getSchema() {
        return Food.FOOD;
    }

    @Override
    public Identity<DishRecord, Integer> getIdentity() {
        return Keys.IDENTITY_DISH;
    }

    @Override
    public UniqueKey<DishRecord> getPrimaryKey() {
        return Keys.DISH_PKEY;
    }

    @Override
    public List<UniqueKey<DishRecord>> getKeys() {
        return Arrays.<UniqueKey<DishRecord>>asList(Keys.DISH_PKEY);
    }

    @Override
    public Dish as(String alias) {
        return new Dish(DSL.name(alias), this);
    }

    @Override
    public Dish as(Name alias) {
        return new Dish(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Dish rename(String name) {
        return new Dish(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Dish rename(Name name) {
        return new Dish(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, Float> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
