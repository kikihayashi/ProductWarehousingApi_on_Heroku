package com.woody.productwarehousingapi.service;

import com.woody.productwarehousingapi.dto.BarcodeItem;

public interface PrintService {
    Integer createBarcode(BarcodeItem barcodeItem);

    void printBarcode(BarcodeItem barcodeItemNew);

    BarcodeItem getBarcodeById(Integer id);

    BarcodeItem getBarcodeByQrcode(String qrCode);
}
