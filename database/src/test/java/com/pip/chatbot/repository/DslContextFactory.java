package com.pip.chatbot.repository;


import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.io.IOException;

public class DslContextFactory {


    public DSLContext getDslContext() throws ClassNotFoundException, IOException {
        Class.forName("org.postgresql.Driver");
        return DSL.using("jdbc:postgresql://trainings/appdb_test1", "app", "Ao4eiT2w");
    }

}
