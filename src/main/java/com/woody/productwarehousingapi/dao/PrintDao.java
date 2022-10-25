package com.woody.productwarehousingapi.dao;

import com.woody.productwarehousingapi.dto.BarcodeItem;
import com.woody.productwarehousingapi.dto.PalletItem;
import com.woody.productwarehousingapi.dto.PalletItemWithNo;

import java.util.List;

public interface PrintDao {
    Integer createBarcode(BarcodeItem barcodeItem);

    Integer createPallet(PalletItem palletItem, String pallet);

    BarcodeItem getBarcodeById(Integer id);

    BarcodeItem getBarcodeByQrcode(String qrCode);

    List<BarcodeItem> getBarcodeList(PalletItem palletItem);

    PalletItemWithNo getPalletById(Integer id);

    PalletItemWithNo getPalletByNo(String palletNo);

    boolean checkIfPalletExist(String pallet);

    void collectBarcode(String pallet);

}
