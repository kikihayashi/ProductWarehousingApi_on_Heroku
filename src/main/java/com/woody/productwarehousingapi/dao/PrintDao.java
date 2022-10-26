package com.woody.productwarehousingapi.dao;

import com.woody.productwarehousingapi.dto.BarcodeItem;
import com.woody.productwarehousingapi.dto.InvalidPalletRequest;
import com.woody.productwarehousingapi.dto.PalletItem;
import com.woody.productwarehousingapi.dto.PalletItemWithNo;

public interface PrintDao {
    boolean checkIfBarcodeExist(String qrCode);

    boolean checkIfPalletExist(String pallet);

    boolean checkIfBarcodeCanCollect(PalletItem palletItem);

    Integer createBarcode(BarcodeItem barcodeItem);

    Integer createPallet(PalletItem palletItem, String pallet);

    BarcodeItem getBarcodeById(Integer id);

    PalletItemWithNo getPalletById(Integer id);

    void collectBarcode(PalletItem palletItem, String pallet);

    void invalidBarcode(InvalidPalletRequest.SerialQuery serialQuery);
}
