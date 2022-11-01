package com.woody.productwarehousingapi.dao.impl;

import com.woody.productwarehousingapi.dao.PrintDao;
import com.woody.productwarehousingapi.dto.BarcodeItem;
import com.woody.productwarehousingapi.dto.InvalidPalletRequest;
import com.woody.productwarehousingapi.dto.PalletItem;
import com.woody.productwarehousingapi.dto.PalletItemWithNo;
import com.woody.productwarehousingapi.rowmapper.BarcodeItemRowMapper;
import com.woody.productwarehousingapi.rowmapper.PalletItemRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PrintDaoImpl implements PrintDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public boolean checkIfBarcodeExist(String qrCode) {
        String sqlCommand = "SELECT COUNT(1) FROM barcode_list WHERE qrcode = :qrcode";

        Map<String, Object> map = new HashMap<>();
        map.put("qrcode", qrCode);

        Integer count = namedParameterJdbcTemplate.queryForObject(sqlCommand, map, Integer.class);

        return count > 0;
    }

    @Override
    public boolean checkIfPalletExist(String pallet) {
        String sqlCommand = "SELECT COUNT(1) FROM pallet_list WHERE pallet_no = :pallet";

        Map<String, Object> map = new HashMap<>();
        map.put("pallet", pallet);

        Integer count = namedParameterJdbcTemplate.queryForObject(sqlCommand, map, Integer.class);

        return count > 0;
    }

    @Override
    public boolean checkIfBarcodeCanCollect(PalletItem palletItem) {
        String sqlCommand = "SELECT COUNT(*) FROM barcode_list WHERE order_no = :orderNo " +
                "AND product_id = :productNo AND lot_no = :lotNo AND pallet_status = :palletStatus";

        String orderNo = palletItem.getOrderId();
        String productNo = palletItem.getProductId();
        String lotNo = palletItem.getLotId();
        String palletStatus = "N";
        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", orderNo);
        map.put("productNo", productNo);
        map.put("lotNo", lotNo);
        map.put("palletStatus", palletStatus);

        Integer count = namedParameterJdbcTemplate.queryForObject(sqlCommand, map, Integer.class);

        return count > 0;
    }

    @Override
    public Integer createBarcode(BarcodeItem barcodeItem) {
        String sqlCommand = "INSERT INTO barcode_list (order_no, product_id ,product_name, lot_no, lot_name, serial_no, qrcode," +
                "valid_day, weight_max, weight_min, pallet_no, pallet_status, created_date, last_modified_date) " +
                "VALUES(:orderNo, :productId, :productName, :lotNo, :lotName, :serialNo, :qrCode, :validDay, :weightMax, " +
                ":weightMin, :palletNo, :palletStatus, :createdDate, :lastModifiedDate)";

        String regex = ";";
        String orderNo = barcodeItem.getQrcode().split(regex)[0];
        String lotNo = barcodeItem.getQrcode().split(regex)[1];
        String productId = barcodeItem.getQrcode().split(regex)[2];
        String serialNo = barcodeItem.getQrcode().split(regex)[6];
        String palletNo = "";
        String palletStatus = "N";
        Date now = new Date();

        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", orderNo);
        map.put("productId", productId);
        map.put("productName", barcodeItem.getProductName());
        map.put("lotNo", lotNo);
        map.put("lotName", barcodeItem.getLotName());
        map.put("serialNo", serialNo);
        map.put("qrCode", barcodeItem.getQrcode());
        map.put("validDay", barcodeItem.getValidDay());
        map.put("weightMax", barcodeItem.getWeightMax());
        map.put("weightMin", barcodeItem.getWeightMin());
        map.put("palletNo", palletNo);
        map.put("palletStatus", palletStatus);
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyholder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sqlCommand, new MapSqlParameterSource(map), keyholder);

        int barcodeId = keyholder.getKey().intValue();

        return barcodeId;
    }

    @Override
    public Integer createPallet(PalletItem palletItem, String pallet) {
        String sqlCommand = "INSERT INTO pallet_list (pallet_no, weight_set, make_date, upload_status, created_date, last_modified_date) " +
                "VALUES(:palletNo, :weightSet, :makeDate, :uploadStatus, :createdDate, :lastModifiedDate)";

        Date now = new Date();

        Map<String, Object> map = new HashMap<>();
        map.put("palletNo", pallet);
        map.put("weightSet", palletItem.getWeightSet());
        map.put("makeDate", palletItem.getMakeDate());
        map.put("uploadStatus", "N");
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyholder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sqlCommand, new MapSqlParameterSource(map), keyholder);

        int palletId = keyholder.getKey().intValue();

        return palletId;
    }

    @Override
    public BarcodeItem getBarcodeById(Integer id) {
        String sqlCommand = "SELECT product_name, lot_name, qrcode, valid_day, weight_max, weight_min " +
                "FROM barcode_list WHERE barcode_id = :barcodeId";

        Map<String, Object> map = new HashMap<>();
        map.put("barcodeId", id);

        List<BarcodeItem> barcodeItemList = namedParameterJdbcTemplate.query(sqlCommand, map, new BarcodeItemRowMapper());

        return (barcodeItemList.size() > 0) ? barcodeItemList.get(0) : null;
    }

    @Override
    public PalletItemWithNo getPalletById(Integer id) {
        String sqlCommand = "SELECT pallet_no, weight_set, make_date " +
                "FROM pallet_list WHERE pallet_id = :palletId";

        Map<String, Object> map = new HashMap<>();
        map.put("palletId", id);

        List<PalletItemWithNo> palletItemList = namedParameterJdbcTemplate.query(sqlCommand, map, new PalletItemRowMapper());

        return (palletItemList.size() > 0) ? palletItemList.get(0) : null;
    }

    @Override
    public void collectBarcode(PalletItem palletItem, String pallet) {
        String sqlCommand = "UPDATE barcode_list SET pallet_no = :palletNo, pallet_status = :palletStatusNew, last_modified_date = :lastModifiedDate " +
                "WHERE order_no = :orderNo AND product_id = :productId AND lot_no = :lotNo AND pallet_status = :palletStatusOld";

        String palletStatusOld = "N";
        String palletStatusNew = "Y";
        String orderNo = palletItem.getOrderId();
        String productNo = palletItem.getProductId();
        String lotNo = palletItem.getLotId();
        Date now = new Date();

        Map<String, Object> map = new HashMap<>();
        map.put("palletNo", pallet);
        map.put("palletStatusNew", palletStatusNew);
        map.put("lastModifiedDate", now);
        map.put("orderNo", orderNo);
        map.put("productId", productNo);
        map.put("lotNo", lotNo);
        map.put("palletStatusOld", palletStatusOld);

        namedParameterJdbcTemplate.update(sqlCommand, map);
    }

    @Override
    public void invalidBarcode(InvalidPalletRequest.SerialQuery serialQuery) {
        String sqlCommand = "UPDATE barcode_list SET pallet_status = :palletStatusNew, last_modified_date = :lastModifiedDate " +
                "WHERE order_no = :orderNo AND product_id = :productId AND lot_no = :lotNo AND pallet_status =  :palletStatusOld";

        String palletStatusNew = "I";
        String palletStatusOld = "N";
        String orderNo = serialQuery.getOrderId();
        String productNo = serialQuery.getProductId();
        String lotNo = serialQuery.getLotId();
        Date now = new Date();

        Map<String, Object> map = new HashMap<>();
        map.put("palletStatusNew", palletStatusNew);
        map.put("orderNo", orderNo);
        map.put("productId", productNo);
        map.put("lotNo", lotNo);
        map.put("lastModifiedDate", now);
        map.put("palletStatusOld", palletStatusOld);

        namedParameterJdbcTemplate.update(sqlCommand, map);
    }
}
