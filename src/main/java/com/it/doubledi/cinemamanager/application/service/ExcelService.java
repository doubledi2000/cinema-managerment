package com.it.doubledi.cinemamanager.application.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface ExcelService {

    void downloadShowtimeTemplate(HttpServletResponse response);

    void uploadShowtime(MultipartFile file);
}
