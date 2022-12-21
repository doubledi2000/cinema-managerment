package com.it.doubledi.cinemamanager.presentation.web;

import com.it.doubledi.cinemamanager._common.model.dto.response.Response;
import com.it.doubledi.cinemamanager.domain.File;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api")
public interface FileResource {

    @PostMapping("/files")
    Response<File> upload(@RequestParam("file")MultipartFile file) throws IOException;

    @GetMapping("/files/{id}")
    ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("id") String id);

}
