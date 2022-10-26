package com.woody.productwarehousingapi.service.impl;

import com.woody.productwarehousingapi.dao.PrintDao;
import com.woody.productwarehousingapi.dto.BarcodeItem;
import com.woody.productwarehousingapi.dto.InvalidPalletRequest;
import com.woody.productwarehousingapi.dto.PalletItem;
import com.woody.productwarehousingapi.dto.PalletItemWithNo;
import com.woody.productwarehousingapi.service.PrintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class PrintServiceImpl implements PrintService {

    private static final Logger log = LoggerFactory.getLogger(PrintServiceImpl.class);

    @Autowired
    private PrintDao printDao;

    @Override
    public boolean checkIfBarcodeExist(String qrCode) {
        return printDao.checkIfBarcodeExist(qrCode);
    }

    @Override
    public boolean checkIfPalletExist(String palletNo) {
        return printDao.checkIfPalletExist(palletNo);
    }

    @Override
    public Integer createBarcode(BarcodeItem barcodeItem) {
        String qrCode = barcodeItem.getQrcode();

        if (printDao.checkIfBarcodeExist(qrCode)) {
            log.warn("條碼: {} 已存在，不可列印！", qrCode);
            return -1;
        }
        return printDao.createBarcode(barcodeItem);
    }

    @Transactional
    @Override
    public Integer createPallet(PalletItem palletItem) {
        if (printDao.checkIfBarcodeCanCollect(palletItem)) {
            //製作棧板號
            String pallet = getPallet();
            while (printDao.checkIfPalletExist(pallet)) {
                pallet = getPallet();
            }
            //綁定條碼
            printDao.collectBarcode(palletItem, pallet);
            //新增棧板
            return printDao.createPallet(palletItem, pallet);
        } else {
            log.warn("綁定棧板失敗，沒有符合條件的條碼可以綁定！");
            return -1;
        }
    }

    @Override
    public BarcodeItem getBarcodeById(Integer id) {
        return printDao.getBarcodeById(id);
    }

    @Override
    public PalletItemWithNo getPalletById(Integer id) {
        return printDao.getPalletById(id);
    }

    @Transactional
    @Override
    public void invalidBarcode(List<InvalidPalletRequest.SerialQuery> serialQueryList) {
        //作廢條碼
        printDao.invalidBarcode(serialQueryList.get(0));
        log.info("作廢棧板成功！");
    }

    @Override
    public void printBarcode(String printIp, BarcodeItem barcodeItem) {
        //列印Barcode
        log.info("列印機(" + printIp + ")-列印條碼：" + barcodeItem.getQrcode());
    }

    @Override
    public void printPallet(String pdaId, String printIp, PalletItemWithNo palletItem) {
        //列印Pallet
        log.info("PDA號碼-" + pdaId + "/列印機(" + printIp + ")-列印棧板：" + palletItem.getPalletNo());
    }

    private String getPallet() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd-HH-mm-ss");
        String[] time = dateFormat.format(date).split("-");
        String pallet = time[0]
                + (char) (65 + (Integer.valueOf(time[1]) + Integer.valueOf(time[2])) % 26)
                + (char) (65 + Math.round((Integer.valueOf(time[3]) + Integer.valueOf(time[4]) + Integer.valueOf(time[5])) * Math.random()) % 26)
                + (Integer.valueOf(time[3]) + Integer.valueOf(time[4]))
                + time[5];
        return pallet;
    }
}
