package com.pip.chatbot.repository;


import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DslContextFactory {


    public DSLContext getDslContext() {
        try (InputStream input = DslContextFactory.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            return DSL.using(prop.getProperty("db.url"), prop.getProperty("db.user"), prop.getProperty("db.password"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
