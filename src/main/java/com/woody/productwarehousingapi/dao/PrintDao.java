package com.woody.productwarehousingapi.dao;

import com.woody.productwarehousingapi.dto.BarcodeItem;

public interface PrintDao {
    BarcodeItem getBarcodeByQrcode(String qrCode);

    Integer createBarcode(BarcodeItem barcodeItem);

    BarcodeItem getBarcodeById(Integer id);
}
