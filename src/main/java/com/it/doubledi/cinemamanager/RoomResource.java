package com.it.doubledi.cinemamanager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface RoomResource {

    @GetMapping("/hello")
    String hello();
}
