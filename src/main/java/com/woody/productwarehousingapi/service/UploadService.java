package com.woody.productwarehousingapi.service;

import com.woody.productwarehousingapi.dto.UploadRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    void store(MultipartFile file);

    String storeProduct(UploadRequest uploadRequest);
}
