package com.pip.chatbot.service;

import com.pip.chatbot.dto.JokeDto;
import com.pip.chatbot.jooq.jokes.tables.records.JokeRecord;
import com.pip.chatbot.repository.JokesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JokesService {
    @Autowired
    JokesRepository jokesRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<JokeDto> getJokeList() throws SQLException {
        var jokeList = jokesRepository.getJokeList();
        var jokeDtoList = new ArrayList<JokeDto>();

        jokeList.forEach(joke -> {
            jokeDtoList.add(modelMapper.map(joke, JokeDto.class));
        });

        return jokeDtoList;
    }

    public JokeDto getJoke(int id) throws SQLException {
        return modelMapper.map(jokesRepository.getJoke(id), JokeDto.class);
    }

    public JokeDto postJoke(JokeDto joke) throws SQLException {
        return modelMapper.map(jokesRepository.postJoke(joke), JokeDto.class);
    }

    public JokeDto updateJoke(int id, JokeDto joke) throws SQLException {
        return modelMapper.map(jokesRepository.updateJoke(id, joke), JokeDto.class);
    }

    public boolean deleteJoke(int id) throws SQLException {
        return jokesRepository.deleteJoke(id);
    }
}
