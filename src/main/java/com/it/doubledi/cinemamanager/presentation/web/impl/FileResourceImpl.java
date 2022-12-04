package com.it.doubledi.cinemamanager.presentation.web.impl;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.application.service.FileService;
import com.it.doubledi.cinemamanager.domain.File;
import com.it.doubledi.cinemamanager.presentation.web.FileResource;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class FileResourceImpl implements FileResource {

    private final FileService fileService;

    @Override
    public Response<File> upload(MultipartFile file) {
        return Response.of(fileService.upload(file));
    }

    @Override
    public ResponseEntity<ByteArrayResource> downloadFile(String id) {
        return null;
    }
}
