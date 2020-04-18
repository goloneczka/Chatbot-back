package com.pip.chatbot.repository.joke;

import com.pip.chatbot.jooq.jokes.tables.records.MarkRecord;
import com.pip.chatbot.model.joke.Mark;
import org.jooq.DSLContext;
import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Mark.MARK;

public class MarkRepository {

    private DSLContext dsl;

    public MarkRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<Mark> createMark(Mark mark) {

        MarkRecord result = dsl.insertInto(MARK)
                .set(MARK.JOKE_ID, mark.getJoke_id())
                .set(MARK.MARK_, mark.getMark())
                .returning()
                .fetchOne();

        return Optional.ofNullable(result.into(Mark.class));
    }
}
