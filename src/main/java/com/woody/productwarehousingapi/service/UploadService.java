package com.woody.productwarehousingapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    void store(MultipartFile file);

}
