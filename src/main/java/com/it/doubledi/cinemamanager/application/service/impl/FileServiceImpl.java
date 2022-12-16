package com.it.doubledi.cinemamanager.application.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.it.doubledi.cinemamanager.application.service.FileService;
import com.it.doubledi.cinemamanager.domain.File;
import com.it.doubledi.cinemamanager.domain.command.FileCreateCmd;
import com.it.doubledi.cinemamanager.domain.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private String bucket = "doubledi-cinema.appspot.com";

    private final FileRepository fileRepository;
    private StorageOptions storageOptions;
    private final String prefix = "https://firebasestorage.googleapis.com/v0/b/doubledi-cinema.appspot.com/o/";
    private final String postfix = "?alt=media";

    public FileServiceImpl(AmazonS3 s3client, FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @PostConstruct
    private void initalizeFirebase() throws IOException {
        InputStream data = Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("firebase/doubledi-cinema-firebase-adminsdk-njo8w-c57accce4a.json"));
        this.storageOptions = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(data))
                .build();
    }

    @Override
    @Transactional
    public File upload(MultipartFile file) {
        java.io.File fileObj = convertMultiPartFileToFile(file);
        if (Objects.isNull(fileObj)) {
            return null;
        }
        java.io.File convertFile = this.convertMultiPartFileToFile(file);
        Path filePath = convertFile.toPath();
        Storage storage = storageOptions.getService();
        String filename = UUID.randomUUID() + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucket, filename);

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        try {
            storage.create(blobInfo, Files.readAllBytes(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileCreateCmd cmd = FileCreateCmd.builder()
                .originalName(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .path(prefix + filename + postfix)
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
