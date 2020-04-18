package com.pip.chatbot.service.joke;

import com.pip.chatbot.model.joke.Mark;
import com.pip.chatbot.repository.joke.MarkRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MarkService {

    private MarkRepository markRepository;

    public MarkService(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    public Optional<Mark> rateJoke(Mark mark) {
        return markRepository.createMark(mark);
    }
}
