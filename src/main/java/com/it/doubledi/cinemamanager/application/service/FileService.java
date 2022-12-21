package com.it.doubledi.cinemamanager.application.service;

import com.it.doubledi.cinemamanager.domain.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    File upload(MultipartFile file) throws IOException;

    byte[] download(String fileId);

    void delete(String fileId);

}
