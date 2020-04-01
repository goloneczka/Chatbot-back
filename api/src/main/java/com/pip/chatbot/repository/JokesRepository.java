package com.pip.chatbot.repository;

import com.pip.chatbot.dto.JokeDto;
import com.pip.chatbot.jooq.jokes.tables.records.JokeRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;

@Repository
public class JokesRepository {
    private static String url = "jdbc:postgresql://localhost:5432/appdb_test1";
    private static String username = "app";
    private static String password = "Ao4eiT2w";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    private DSLContext getDSLContext() throws SQLException {
        var con = connect();
        return DSL.using(con, SQLDialect.POSTGRES);
    }

    public List<JokeRecord> getJokeList() throws SQLException {
        return getDSLContext()
                .selectFrom(JOKE)
                .fetchInto(JokeRecord.class);
    }

    public JokeRecord getJoke(int id) throws SQLException {
         return (JokeRecord) getDSLContext()
                 .fetchOne(JOKE, JOKE.ID.eq(id));
    }

    public JokeRecord postJoke(JokeDto joke) throws SQLException {
         Result<JokeRecord> result = getDSLContext()
                .insertInto(JOKE, JOKE.CATEGORY, JOKE.JOKE_)
                .values(joke.getCategory(), joke.getJoke())
                .returning(JOKE.ID, JOKE.CATEGORY, JOKE.JOKE_)
                .fetch();

         return result.get(0);
    }

    public JokeRecord updateJoke(int id, JokeDto joke) throws SQLException {
        Result<JokeRecord> result = getDSLContext()
                .update(JOKE)
                .set(JOKE.CATEGORY, joke.getCategory())
                .set(JOKE.JOKE_, joke.getJoke())
                .where(JOKE.ID.eq(id))
                .returning(JOKE.ID, JOKE.CATEGORY, JOKE.JOKE_)
                .fetch();

        return result.get(0);
    }

    public boolean deleteJoke(int id) throws SQLException {
        return 0 < getDSLContext()
                .deleteFrom(JOKE)
                .where(JOKE.ID.eq(id))
                .execute();
    }
}
