package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager.RoomResource;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomResourceImpl implements RoomResource {
    @Override
    public String hello() {
        return "hiii";
    }
}
