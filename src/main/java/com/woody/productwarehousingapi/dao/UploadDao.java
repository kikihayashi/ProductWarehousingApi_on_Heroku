package com.woody.productwarehousingapi.dao;

import com.woody.productwarehousingapi.dto.UploadRequest;

public interface UploadDao {
    String storeProduct(UploadRequest uploadRequest);
}
