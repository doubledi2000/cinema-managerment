package com.it.doubledi.cinemamanager.application.service.impl;

import com.it.doubledi.cinemamanager._common.model.dto.PageDTO;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeCreateRequest;
import com.it.doubledi.cinemamanager.application.dto.request.ShowtimeSearchRequest;
import com.it.doubledi.cinemamanager.application.mapper.AutoMapper;
import com.it.doubledi.cinemamanager.application.service.ShowtimeService;
import com.it.doubledi.cinemamanager.domain.Showtime;
import com.it.doubledi.cinemamanager.domain.repository.FilmRepository;
import com.it.doubledi.cinemamanager.domain.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class ShowtimeServiceImpl implements ShowtimeService {
    private RoomRepository repository;
    private FilmRepository filmRepository;
    private AutoMapper autoMapper;


    @Override
    public Showtime create(ShowtimeCreateRequest request) {
        return null;
    }

    @Override
    public Showtime getById(String id) {
        return null;
    }

    @Override
    public PageDTO<Showtime> search(ShowtimeSearchRequest request) {
        return null;
    }

    @Override
    public PageDTO<Showtime> autoComplete(ShowtimeSearchRequest request) {
        return null;
    }
}
