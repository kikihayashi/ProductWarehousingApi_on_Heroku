package com.woody.productwarehousingapi.service;

import com.woody.productwarehousingapi.dto.UploadRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadService {

    void store(MultipartFile file);

    String storeProduct(UploadRequest uploadRequest);

    boolean checkIfPalletUploaded(List<UploadRequest.AllSerialNo> allSerialNoList);
}
