/*
 * This file is generated by jOOQ.
 */
package com.pip.chatbot.jooq.food.tables;


import com.pip.chatbot.jooq.food.Food;
import com.pip.chatbot.jooq.food.Keys;
import com.pip.chatbot.jooq.food.tables.records.RestaurantCuisineRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
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
public class RestaurantCuisine extends TableImpl<RestaurantCuisineRecord> {

    private static final long serialVersionUID = -743697290;

    /**
     * The reference instance of <code>food.restaurant_cuisine</code>
     */
    public static final RestaurantCuisine RESTAURANT_CUISINE = new RestaurantCuisine();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RestaurantCuisineRecord> getRecordType() {
        return RestaurantCuisineRecord.class;
    }

    /**
     * The column <code>food.restaurant_cuisine.restaurant_id</code>.
     */
    public final TableField<RestaurantCuisineRecord, Integer> RESTAURANT_ID = createField(DSL.name("restaurant_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('food.restaurant_cuisine_restaurant_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>food.restaurant_cuisine.cuisine</code>.
     */
    public final TableField<RestaurantCuisineRecord, String> CUISINE = createField(DSL.name("cuisine"), org.jooq.impl.SQLDataType.VARCHAR(127).nullable(false), this, "");

    /**
     * Create a <code>food.restaurant_cuisine</code> table reference
     */
    public RestaurantCuisine() {
        this(DSL.name("restaurant_cuisine"), null);
    }

    /**
     * Create an aliased <code>food.restaurant_cuisine</code> table reference
     */
    public RestaurantCuisine(String alias) {
        this(DSL.name(alias), RESTAURANT_CUISINE);
    }

    /**
     * Create an aliased <code>food.restaurant_cuisine</code> table reference
     */
    public RestaurantCuisine(Name alias) {
        this(alias, RESTAURANT_CUISINE);
    }

    private RestaurantCuisine(Name alias, Table<RestaurantCuisineRecord> aliased) {
        this(alias, aliased, null);
    }

    private RestaurantCuisine(Name alias, Table<RestaurantCuisineRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> RestaurantCuisine(Table<O> child, ForeignKey<O, RestaurantCuisineRecord> key) {
        super(child, key, RESTAURANT_CUISINE);
    }

    @Override
    public Schema getSchema() {
        return Food.FOOD;
    }

    @Override
    public Identity<RestaurantCuisineRecord, Integer> getIdentity() {
        return Keys.IDENTITY_RESTAURANT_CUISINE;
    }

    @Override
    public UniqueKey<RestaurantCuisineRecord> getPrimaryKey() {
        return Keys.RESTAURANT_CUISINE_PKEY;
    }

    @Override
    public List<UniqueKey<RestaurantCuisineRecord>> getKeys() {
        return Arrays.<UniqueKey<RestaurantCuisineRecord>>asList(Keys.RESTAURANT_CUISINE_PKEY);
    }

    @Override
    public List<ForeignKey<RestaurantCuisineRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<RestaurantCuisineRecord, ?>>asList(Keys.RESTAURANT_CUISINE__RESTAURANT_CUISINE_RESTAURANT_ID_FKEY, Keys.RESTAURANT_CUISINE__RESTAURANT_CUISINE_CUISINE_FKEY);
    }

    public Restaurant restaurant() {
        return new Restaurant(this, Keys.RESTAURANT_CUISINE__RESTAURANT_CUISINE_RESTAURANT_ID_FKEY);
    }

    public Cuisine cuisine() {
        return new Cuisine(this, Keys.RESTAURANT_CUISINE__RESTAURANT_CUISINE_CUISINE_FKEY);
    }

    @Override
    public RestaurantCuisine as(String alias) {
        return new RestaurantCuisine(DSL.name(alias), this);
    }

    @Override
    public RestaurantCuisine as(Name alias) {
        return new RestaurantCuisine(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public RestaurantCuisine rename(String name) {
        return new RestaurantCuisine(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public RestaurantCuisine rename(Name name) {
        return new RestaurantCuisine(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
