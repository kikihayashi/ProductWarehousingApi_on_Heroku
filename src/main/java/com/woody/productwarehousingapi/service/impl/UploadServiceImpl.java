package com.woody.productwarehousingapi.service.impl;

import com.woody.productwarehousingapi.configuration.StorageConfiguration;
import com.woody.productwarehousingapi.dao.UploadDao;
import com.woody.productwarehousingapi.dto.UploadRequest;
import com.woody.productwarehousingapi.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UploadServiceImpl implements UploadService {

    private static final Logger log = LoggerFactory.getLogger(UploadServiceImpl.class);

    private final Path rootLocation;

    @Autowired
    private UploadDao uploadDao;

    @Autowired
    public UploadServiceImpl(StorageConfiguration configuration) {
        this.rootLocation = Paths.get(configuration.getLocation());
    }

    @Transactional
    @Override
    public String storeProduct(UploadRequest uploadRequest) {

        String warehouseNo = uploadDao.storeProduct(uploadRequest);

        uploadDao.createWarehouse(warehouseNo, uploadRequest);

        return warehouseNo;
    }

    @Override
    public boolean checkIfPalletUploaded(List<UploadRequest.AllSerialNo> allSerialNoList) {
        Set<String> palletSet = new HashSet<>();
        for (UploadRequest.AllSerialNo serialNo : allSerialNoList) {
            palletSet.add(serialNo.getPalletNo());
        }

        for (String pallet : palletSet) {
            if (uploadDao.checkIfPalletUploaded(pallet)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (!Files.isDirectory(rootLocation)) {
                Files.createDirectory(rootLocation);
            }
            if (file.isEmpty()) {
                log.error("Failed to store empty file " + file.getOriginalFilename());
            }
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd-HHmmss");
            String uploadFileName = dateFormat.format(date) + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(uploadFileName));
        } catch (IOException e) {
            log.error("Failed to store file " + file.getOriginalFilename() + "\n" + e);
        }
    }
}
