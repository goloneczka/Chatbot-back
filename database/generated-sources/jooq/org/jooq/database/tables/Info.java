/*
 * This file is generated by jOOQ.
 */
package org.jooq.database.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row10;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.database.Keys;
import org.jooq.database.Weather;
import org.jooq.database.tables.records.InfoRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Info extends TableImpl<InfoRecord> {

    private static final long serialVersionUID = 1944461444;

    /**
     * The reference instance of <code>weather.info</code>
     */
    public static final Info INFO = new Info();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<InfoRecord> getRecordType() {
        return InfoRecord.class;
    }

    /**
     * The column <code>weather.info.id</code>.
     */
    public final TableField<InfoRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('weather.info_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>weather.info.created_on</code>.
     */
    public final TableField<InfoRecord, LocalDateTime> CREATED_ON = createField(DSL.name("created_on"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>weather.info.date</code>.
     */
    public final TableField<InfoRecord, LocalDateTime> DATE = createField(DSL.name("date"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>weather.info.temperature</code>.
     */
    public final TableField<InfoRecord, Float> TEMPERATURE = createField(DSL.name("temperature"), org.jooq.impl.SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>weather.info.perceived_temperature</code>.
     */
    public final TableField<InfoRecord, Float> PERCEIVED_TEMPERATURE = createField(DSL.name("perceived_temperature"), org.jooq.impl.SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>weather.info.wind_power</code>.
     */
    public final TableField<InfoRecord, Float> WIND_POWER = createField(DSL.name("wind_power"), org.jooq.impl.SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>weather.info.wind_direction</code>.
     */
    public final TableField<InfoRecord, String> WIND_DIRECTION = createField(DSL.name("wind_direction"), org.jooq.impl.SQLDataType.CHAR(2).nullable(false), this, "");

    /**
     * The column <code>weather.info.atmospheric_pressure</code>.
     */
    public final TableField<InfoRecord, Float> ATMOSPHERIC_PRESSURE = createField(DSL.name("atmospheric_pressure"), org.jooq.impl.SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>weather.info.air_humidity</code>.
     */
    public final TableField<InfoRecord, Float> AIR_HUMIDITY = createField(DSL.name("air_humidity"), org.jooq.impl.SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>weather.info.city</code>.
     */
    public final TableField<InfoRecord, String> CITY = createField(DSL.name("city"), org.jooq.impl.SQLDataType.VARCHAR(25).nullable(false), this, "");

    /**
     * Create a <code>weather.info</code> table reference
     */
    public Info() {
        this(DSL.name("info"), null);
    }

    /**
     * Create an aliased <code>weather.info</code> table reference
     */
    public Info(String alias) {
        this(DSL.name(alias), INFO);
    }

    /**
     * Create an aliased <code>weather.info</code> table reference
     */
    public Info(Name alias) {
        this(alias, INFO);
    }

    private Info(Name alias, Table<InfoRecord> aliased) {
        this(alias, aliased, null);
    }

    private Info(Name alias, Table<InfoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Info(Table<O> child, ForeignKey<O, InfoRecord> key) {
        super(child, key, INFO);
    }

    @Override
    public Schema getSchema() {
        return Weather.WEATHER;
    }

    @Override
    public Identity<InfoRecord, Integer> getIdentity() {
        return Keys.IDENTITY_INFO;
    }

    @Override
    public UniqueKey<InfoRecord> getPrimaryKey() {
        return Keys.INFO_PKEY;
    }

    @Override
    public List<UniqueKey<InfoRecord>> getKeys() {
        return Arrays.<UniqueKey<InfoRecord>>asList(Keys.INFO_PKEY);
    }

    @Override
    public Info as(String alias) {
        return new Info(DSL.name(alias), this);
    }

    @Override
    public Info as(Name alias) {
        return new Info(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Info rename(String name) {
        return new Info(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Info rename(Name name) {
        return new Info(name, null);
    }

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row10<Integer, LocalDateTime, LocalDateTime, Float, Float, Float, String, Float, Float, String> fieldsRow() {
        return (Row10) super.fieldsRow();
    }
}
