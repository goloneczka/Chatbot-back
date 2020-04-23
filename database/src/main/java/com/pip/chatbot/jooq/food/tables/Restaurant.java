/*
 * This file is generated by jOOQ.
 */
package com.pip.chatbot.jooq.food.tables;


import com.pip.chatbot.jooq.food.Food;
import com.pip.chatbot.jooq.food.Keys;
import com.pip.chatbot.jooq.food.tables.records.RestaurantRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row6;
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
public class Restaurant extends TableImpl<RestaurantRecord> {

    private static final long serialVersionUID = 210065122;

    /**
     * The reference instance of <code>food.restaurant</code>
     */
    public static final Restaurant RESTAURANT = new Restaurant();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RestaurantRecord> getRecordType() {
        return RestaurantRecord.class;
    }

    /**
     * The column <code>food.restaurant.id</code>.
     */
    public final TableField<RestaurantRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('food.restaurant_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>food.restaurant.name</code>.
     */
    public final TableField<RestaurantRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(127).nullable(false), this, "");

    /**
     * The column <code>food.restaurant.address</code>.
     */
    public final TableField<RestaurantRecord, String> ADDRESS = createField(DSL.name("address"), org.jooq.impl.SQLDataType.VARCHAR(127).nullable(false), this, "");

    /**
     * The column <code>food.restaurant.city</code>.
     */
    public final TableField<RestaurantRecord, String> CITY = createField(DSL.name("city"), org.jooq.impl.SQLDataType.VARCHAR(127).nullable(false), this, "");

    /**
     * The column <code>food.restaurant.average_users_rating</code>.
     */
    public final TableField<RestaurantRecord, Float> AVERAGE_USERS_RATING = createField(DSL.name("average_users_rating"), org.jooq.impl.SQLDataType.REAL, this, "");

    /**
     * The column <code>food.restaurant.phone_numbers</code>.
     */
    public final TableField<RestaurantRecord, String> PHONE_NUMBERS = createField(DSL.name("phone_numbers"), org.jooq.impl.SQLDataType.VARCHAR(127), this, "");

    /**
     * Create a <code>food.restaurant</code> table reference
     */
    public Restaurant() {
        this(DSL.name("restaurant"), null);
    }

    /**
     * Create an aliased <code>food.restaurant</code> table reference
     */
    public Restaurant(String alias) {
        this(DSL.name(alias), RESTAURANT);
    }

    /**
     * Create an aliased <code>food.restaurant</code> table reference
     */
    public Restaurant(Name alias) {
        this(alias, RESTAURANT);
    }

    private Restaurant(Name alias, Table<RestaurantRecord> aliased) {
        this(alias, aliased, null);
    }

    private Restaurant(Name alias, Table<RestaurantRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Restaurant(Table<O> child, ForeignKey<O, RestaurantRecord> key) {
        super(child, key, RESTAURANT);
    }

    @Override
    public Schema getSchema() {
        return Food.FOOD;
    }

    @Override
    public Identity<RestaurantRecord, Integer> getIdentity() {
        return Keys.IDENTITY_RESTAURANT;
    }

    @Override
    public UniqueKey<RestaurantRecord> getPrimaryKey() {
        return Keys.RESTAURANT_PKEY;
    }

    @Override
    public List<UniqueKey<RestaurantRecord>> getKeys() {
        return Arrays.<UniqueKey<RestaurantRecord>>asList(Keys.RESTAURANT_PKEY);
    }

    @Override
    public List<ForeignKey<RestaurantRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<RestaurantRecord, ?>>asList(Keys.RESTAURANT__RESTAURANT_CITY_FKEY);
    }

    public City city() {
        return new City(this, Keys.RESTAURANT__RESTAURANT_CITY_FKEY);
    }

    @Override
    public Restaurant as(String alias) {
        return new Restaurant(DSL.name(alias), this);
    }

    @Override
    public Restaurant as(Name alias) {
        return new Restaurant(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Restaurant rename(String name) {
        return new Restaurant(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Restaurant rename(Name name) {
        return new Restaurant(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, String, String, String, Float, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}
