package com.woody.productwarehousingapi.dao.impl;

import com.woody.productwarehousingapi.dao.PrintDao;
import com.woody.productwarehousingapi.dto.BarcodeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

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
}
