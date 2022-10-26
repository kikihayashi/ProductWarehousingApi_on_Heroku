package com.woody.productwarehousingapi.rowmapper;

import com.woody.productwarehousingapi.dto.BarcodeItem;
import com.woody.productwarehousingapi.dto.InfoItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BarcodeItemRowMapper implements RowMapper<BarcodeItem> {

    @Override
    public BarcodeItem mapRow(ResultSet resultSet, int i) throws SQLException {
        BarcodeItem barcodeItem = new BarcodeItem();

        barcodeItem.setProductName(resultSet.getString("product_name"));
        barcodeItem.setLotName(resultSet.getString("lot_name"));
        barcodeItem.setQrcode(resultSet.getString("qrcode"));
        barcodeItem.setValidDay(resultSet.getString("valid_day"));
        barcodeItem.setWeightMax(resultSet.getString("weight_max"));
        barcodeItem.setWeightMin(resultSet.getString("weight_min"));

        return barcodeItem;
    }
}
