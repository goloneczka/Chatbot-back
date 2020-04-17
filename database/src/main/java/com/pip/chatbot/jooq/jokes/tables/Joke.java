/*
 * This file is generated by jOOQ.
 */
package com.pip.chatbot.jooq.jokes.tables;


import com.pip.chatbot.jooq.jokes.Jokes;
import com.pip.chatbot.jooq.jokes.Keys;
import com.pip.chatbot.jooq.jokes.tables.records.JokeRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
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
public class Joke extends TableImpl<JokeRecord> {

    private static final long serialVersionUID = 812373261;

    /**
     * The reference instance of <code>jokes.joke</code>
     */
    public static final Joke JOKE = new Joke();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JokeRecord> getRecordType() {
        return JokeRecord.class;
    }

    /**
     * The column <code>jokes.joke.id</code>.
     */
    public final TableField<JokeRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('jokes.joke_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>jokes.joke.category</code>.
     */
    public final TableField<JokeRecord, String> CATEGORY = createField(DSL.name("category"), org.jooq.impl.SQLDataType.VARCHAR(127).nullable(false), this, "");

    /**
     * The column <code>jokes.joke.joke</code>.
     */
    public final TableField<JokeRecord, String> JOKE_ = createField(DSL.name("joke"), org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>jokes.joke.is_confirmed</code>.
     */
    public final TableField<JokeRecord, Boolean> IS_CONFIRMED = createField(DSL.name("is_confirmed"), org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.field("true", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * Create a <code>jokes.joke</code> table reference
     */
    public Joke() {
        this(DSL.name("joke"), null);
    }

    /**
     * Create an aliased <code>jokes.joke</code> table reference
     */
    public Joke(String alias) {
        this(DSL.name(alias), JOKE);
    }

    /**
     * Create an aliased <code>jokes.joke</code> table reference
     */
    public Joke(Name alias) {
        this(alias, JOKE);
    }

    private Joke(Name alias, Table<JokeRecord> aliased) {
        this(alias, aliased, null);
    }

    private Joke(Name alias, Table<JokeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Joke(Table<O> child, ForeignKey<O, JokeRecord> key) {
        super(child, key, JOKE);
    }

    @Override
    public Schema getSchema() {
        return Jokes.JOKES;
    }

    @Override
    public Identity<JokeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_JOKE;
    }

    @Override
    public UniqueKey<JokeRecord> getPrimaryKey() {
        return Keys.JOKE_PKEY;
    }

    @Override
    public List<UniqueKey<JokeRecord>> getKeys() {
        return Arrays.<UniqueKey<JokeRecord>>asList(Keys.JOKE_PKEY);
    }

    @Override
    public List<ForeignKey<JokeRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JokeRecord, ?>>asList(Keys.JOKE__JOKE_CATEGORY_FKEY);
    }

    public Category category() {
        return new Category(this, Keys.JOKE__JOKE_CATEGORY_FKEY);
    }

    @Override
    public Joke as(String alias) {
        return new Joke(DSL.name(alias), this);
    }

    @Override
    public Joke as(Name alias) {
        return new Joke(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Joke rename(String name) {
        return new Joke(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Joke rename(Name name) {
        return new Joke(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, String, String, Boolean> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
