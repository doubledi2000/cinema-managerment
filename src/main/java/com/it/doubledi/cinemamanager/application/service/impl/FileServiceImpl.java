package com.it.doubledi.cinemamanager.application.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.it.doubledi.cinemamanager.application.service.FileService;
import com.it.doubledi.cinemamanager.domain.File;
import com.it.doubledi.cinemamanager.domain.command.FileCreateCmd;
import com.it.doubledi.cinemamanager.domain.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${application.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3client;
    private final FileRepository fileRepository;
    private final String prefix = "https://cinema-storage.s3.ap-northeast-1.amazonaws.com/";

    public FileServiceImpl(AmazonS3 s3client, FileRepository fileRepository) {
        this.s3client = s3client;
        this.fileRepository = fileRepository;
    }

    @Override
    @Transactional
    public File upload(MultipartFile file) {
        java.io.File fileObj = convertMultiPartFileToFile(file);
        if (Objects.isNull(fileObj)) {
            return null;
        }
        String url = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3client.putObject(new PutObjectRequest(this.bucketName, url, fileObj).withCannedAcl(CannedAccessControlList.PublicRead));
        FileCreateCmd cmd = FileCreateCmd.builder()
                .originalName(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .path(prefix + url)
                .build();

        File fileDomain = new File(cmd);
        this.fileRepository.save(fileDomain);
        return fileDomain;
    }

    @Override
    public byte[] download(String fileId) {
        return new byte[0];
    }

    @Override
    public void delete(String fileId) {

    }

    private java.io.File convertMultiPartFileToFile(MultipartFile file) {
        String tmp = System.getProperty("java.io.tmpdir");
        java.io.File convFile = new java.io.File(tmp + System.currentTimeMillis() + "_" + file.getOriginalFilename());
        try {
            InputStream is = file.getInputStream();
            Files.copy(is, convFile.toPath());
            return convFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
