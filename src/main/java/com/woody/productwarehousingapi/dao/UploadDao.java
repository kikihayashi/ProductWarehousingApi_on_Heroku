package com.woody.productwarehousingapi.dao;

import com.woody.productwarehousingapi.dto.UploadRequest;

import java.util.Set;

public interface UploadDao {
    String storeProduct(UploadRequest uploadRequest);

    void createWarehouse(String warehouseNo, UploadRequest uploadRequest);

    boolean checkIfPalletUploaded(String pallet);

    void changePalletUploadStatus(Set<String> palletSet);
}
