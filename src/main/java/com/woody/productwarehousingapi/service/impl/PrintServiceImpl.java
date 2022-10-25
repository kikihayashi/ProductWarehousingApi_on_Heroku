package com.woody.productwarehousingapi.service.impl;

import com.woody.productwarehousingapi.dao.PrintDao;
import com.woody.productwarehousingapi.dto.BarcodeItem;
import com.woody.productwarehousingapi.service.PrintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrintServiceImpl implements PrintService {

    private static final Logger log = LoggerFactory.getLogger(PrintServiceImpl.class);

    @Autowired
    private PrintDao printDao;

    @Override
    public Integer createBarcode(BarcodeItem barcodeItem) {
        String qrCode = barcodeItem.getQrcode();

        BarcodeItem barcodeItemCheck = printDao.getBarcodeByQrcode(qrCode);

        if (barcodeItemCheck != null) {
            log.warn("Barcode {} 已存在，不可列印！", qrCode);
            return -1;
        } else {
            return printDao.createBarcode(barcodeItem);
        }
    }

    @Override
    public void printBarcode(BarcodeItem barcodeItem) {
        //列印Barcode
        System.out.println("列印Barcode：" + barcodeItem.getQrcode());
    }

    @Override
    public BarcodeItem getBarcodeById(Integer id) {
        return printDao.getBarcodeById(id);
    }

    @Override
    public BarcodeItem getBarcodeByQrcode(String qrCode) {
        return printDao.getBarcodeByQrcode(qrCode);
    }
}
