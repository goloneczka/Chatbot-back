/*
 * This file is generated by jOOQ.
 */
package com.pip.chatbot.jooq.food.tables.records;


import com.pip.chatbot.jooq.food.tables.Restaurant;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RestaurantRecord extends UpdatableRecordImpl<RestaurantRecord> implements Record6<Integer, String, String, String, Float, String> {

    private static final long serialVersionUID = -233903329;

    /**
     * Setter for <code>food.restaurant.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>food.restaurant.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>food.restaurant.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>food.restaurant.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>food.restaurant.address</code>.
     */
    public void setAddress(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>food.restaurant.address</code>.
     */
    public String getAddress() {
        return (String) get(2);
    }

    /**
     * Setter for <code>food.restaurant.city</code>.
     */
    public void setCity(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>food.restaurant.city</code>.
     */
    public String getCity() {
        return (String) get(3);
    }

    /**
     * Setter for <code>food.restaurant.average_users_rating</code>.
     */
    public void setAverageUsersRating(Float value) {
        set(4, value);
    }

    /**
     * Getter for <code>food.restaurant.average_users_rating</code>.
     */
    public Float getAverageUsersRating() {
        return (Float) get(4);
    }

    /**
     * Setter for <code>food.restaurant.phone_numbers</code>.
     */
    public void setPhoneNumbers(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>food.restaurant.phone_numbers</code>.
     */
    public String getPhoneNumbers() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, String, String, String, Float, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Integer, String, String, String, Float, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Restaurant.RESTAURANT.ID;
    }

    @Override
    public Field<String> field2() {
        return Restaurant.RESTAURANT.NAME;
    }

    @Override
    public Field<String> field3() {
        return Restaurant.RESTAURANT.ADDRESS;
    }

    @Override
    public Field<String> field4() {
        return Restaurant.RESTAURANT.CITY;
    }

    @Override
    public Field<Float> field5() {
        return Restaurant.RESTAURANT.AVERAGE_USERS_RATING;
    }

    @Override
    public Field<String> field6() {
        return Restaurant.RESTAURANT.PHONE_NUMBERS;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getAddress();
    }

    @Override
    public String component4() {
        return getCity();
    }

    @Override
    public Float component5() {
        return getAverageUsersRating();
    }

    @Override
    public String component6() {
        return getPhoneNumbers();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getAddress();
    }

    @Override
    public String value4() {
        return getCity();
    }

    @Override
    public Float value5() {
        return getAverageUsersRating();
    }

    @Override
    public String value6() {
        return getPhoneNumbers();
    }

    @Override
    public RestaurantRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public RestaurantRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public RestaurantRecord value3(String value) {
        setAddress(value);
        return this;
    }

    @Override
    public RestaurantRecord value4(String value) {
        setCity(value);
        return this;
    }

    @Override
    public RestaurantRecord value5(Float value) {
        setAverageUsersRating(value);
        return this;
    }

    @Override
    public RestaurantRecord value6(String value) {
        setPhoneNumbers(value);
        return this;
    }

    @Override
    public RestaurantRecord values(Integer value1, String value2, String value3, String value4, Float value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RestaurantRecord
     */
    public RestaurantRecord() {
        super(Restaurant.RESTAURANT);
    }

    /**
     * Create a detached, initialised RestaurantRecord
     */
    public RestaurantRecord(Integer id, String name, String address, String city, Float averageUsersRating, String phoneNumbers) {
        super(Restaurant.RESTAURANT);

        set(0, id);
        set(1, name);
        set(2, address);
        set(3, city);
        set(4, averageUsersRating);
        set(5, phoneNumbers);
    }
}
