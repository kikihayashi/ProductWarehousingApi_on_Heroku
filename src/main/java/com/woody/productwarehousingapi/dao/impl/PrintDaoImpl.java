package com.woody.productwarehousingapi.dao.impl;

import com.woody.productwarehousingapi.dao.PrintDao;
import com.woody.productwarehousingapi.dto.BarcodeItem;
import com.woody.productwarehousingapi.dto.PalletItem;
import com.woody.productwarehousingapi.dto.PalletItemWithNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrintDaoImpl implements PrintDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public BarcodeItem getBarcodeByQrcode(String qrCode) {
        return null;
    }

    @Override
    public Integer createBarcode(BarcodeItem barcodeItem) {
        return null;
    }

    @Override
    public BarcodeItem getBarcodeById(Integer id) {
        return null;
    }

    @Override
    public Integer createPallet(PalletItem palletItem, String pallet) {
        return null;
    }

    @Override
    public List<BarcodeItem> getBarcodeList(PalletItem palletItem) {
        return null;
    }

    @Override
    public PalletItemWithNo getPalletById(Integer id) {
        return null;
    }

    @Override
    public PalletItemWithNo getPalletByNo(String palletNo) {
        return null;
    }

    @Override
    public boolean checkIfPalletExist(String pallet) {
        return false;
    }

    @Override
    public void collectBarcode(String pallet) {

    }
}
