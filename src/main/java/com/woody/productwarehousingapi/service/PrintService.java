package com.woody.productwarehousingapi.service;

import com.woody.productwarehousingapi.dto.BarcodeItem;
import com.woody.productwarehousingapi.dto.InvalidPalletRequest;
import com.woody.productwarehousingapi.dto.PalletItem;
import com.woody.productwarehousingapi.dto.PalletItemWithNo;

import java.util.List;

public interface PrintService {
    Integer createBarcode(BarcodeItem barcodeItem);

    Integer createPallet(PalletItem palletItem);

    BarcodeItem getBarcodeById(Integer id);

    PalletItemWithNo getPalletById(Integer id);

    boolean checkIfBarcodeExist(String qrCode);

    boolean checkIfPalletExist(String palletNo);

    void invalidBarcode(List<InvalidPalletRequest.SerialQuery> serialQueryList);

    void printBarcode(String printIp, BarcodeItem barcodeItem);

    void printPallet(String pdaId, String printIp, PalletItemWithNo palletItemWithNo);

}
