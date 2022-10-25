package com.woody.productwarehousingapi.service.impl;

import com.woody.productwarehousingapi.configuration.StorageConfiguration;
import com.woody.productwarehousingapi.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UploadServiceImpl implements UploadService {

    private final Path rootLocation;

    @Autowired
    public UploadServiceImpl(StorageConfiguration configuration) {
        this.rootLocation = Paths.get(configuration.getLocation());
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (!Files.isDirectory(rootLocation)) {
                Files.createDirectory(rootLocation);
            }
            if (file.isEmpty()) {
                System.out.println("Failed to store empty file " + file.getOriginalFilename());
            }
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd-HHmmss");
            String uploadFileName = dateFormat.format(date) + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(uploadFileName));
        } catch (IOException e) {
            System.out.println("Failed to store file " + file.getOriginalFilename());
        }
    }
}
