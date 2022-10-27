package com.woody.productwarehousingapi.dao.impl;

import com.woody.productwarehousingapi.dao.UploadDao;
import com.woody.productwarehousingapi.dto.UploadRequest;
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
public class UploadDaoImpl implements UploadDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String storeProduct(UploadRequest uploadRequest) {
        String sqlCommand = "INSERT INTO serial_list (warehouse_no, order_no, prod_id, prod_name, batch_id, serial_no, pallet_no, " +
                "valid_date, est_date, qj_set_weight, pda_id, ware_in_class, ware_id, storage_id, created_date, last_modified_date) " +
                "VALUE(:warehouseNo, :orderNo, :prodId, :prodName, :batchId, :serialNo, :palletNo, :validDate, :estDate, :qjSetWeight, " +
                ":pdaId, :wareInClass, :wareId, :storageId, :createdDate, :lastModifiedDate)";

        List<UploadRequest.AllSerialNo> serialNoList = uploadRequest.getSerialNo().getAllSerialNoList();
        UploadRequest.MasterData masterData = uploadRequest.getData().getMasterDataList().get(0);
        List<UploadRequest.DetailData> detailDataList = masterData.getDetailDataList();
        String warehouseNo = "WHID" + new Date().getTime();
        Date now = new Date();

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[serialNoList.size()];

        Map<String, UploadRequest.DetailData> map = new HashMap<>();
        for (UploadRequest.DetailData detailData : detailDataList) {
            map.put(detailData.getProdID(), detailData);
        }
        for (int i = 0; i < serialNoList.size(); i++) {
            UploadRequest.AllSerialNo item = serialNoList.get(i);
            UploadRequest.DetailData detailData = map.get(item.getProductId());
            UploadRequest.DetailDataSub detailDataSub = detailData.getDetailDataSubList().get(0);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("warehouseNo", warehouseNo);
            parameterSources[i].addValue("orderNo", item.getOrderNo());
            parameterSources[i].addValue("prodId", item.getProductId());
            parameterSources[i].addValue("prodName", detailData.getProdName());
            parameterSources[i].addValue("batchId", item.getBatchId());
            parameterSources[i].addValue("serialNo", item.getSerial());
            parameterSources[i].addValue("palletNo", item.getPalletNo());
            parameterSources[i].addValue("validDate", detailDataSub.getValidDate());
            parameterSources[i].addValue("estDate", detailDataSub.getEstWareInDate());
            parameterSources[i].addValue("qjSetWeight", detailDataSub.getQJSetWeight());
            parameterSources[i].addValue("pdaId", item.getPdaId());
            parameterSources[i].addValue("wareInClass", detailData.getWareInClass());
            parameterSources[i].addValue("wareId", detailData.getWareID());
            parameterSources[i].addValue("storageId", detailDataSub.getStorageID());
            parameterSources[i].addValue("createdDate", now);
            parameterSources[i].addValue("lastModifiedDate", now);
        }
        //一次執行大量SQL語法
        namedParameterJdbcTemplate.batchUpdate(sqlCommand, parameterSources);

        return warehouseNo;
    }

    @Override
    public void createWarehouse(String warehouseNo, UploadRequest uploadRequest) {
        String sqlCommand = "INSERT INTO warehouse_list (warehouse_no, bill_date, order_no, order_date, work_time, " +
                "product_id, product_name, total_quantity, created_date, last_modified_date) " +
                "VALUES(:warehouseNo, :billDate, :orderNo, :orderDate, :workTime, :productId, :productName, :totalQuantity, :createdDate, :lastModifiedDate)";

        UploadRequest.MasterData masterData = uploadRequest.getData().getMasterDataList().get(0);
        int totalQuantity = uploadRequest.getSerialNo().getAllSerialNoList().size();
        Date now = new Date();

        Map<String, Object> map = new HashMap<>();
        map.put("warehouseNo", warehouseNo);
        map.put("billDate", masterData.getBillDate());
        map.put("orderNo",masterData.getOrderNo());
        map.put("orderDate",masterData.getOrderDate());
        map.put("workTime", masterData.getWorkTimeRealProc());
        map.put("productId", masterData.getProductId());
        map.put("productName", masterData.getProductName());
        map.put("totalQuantity", totalQuantity);
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        namedParameterJdbcTemplate.update(sqlCommand, new MapSqlParameterSource(map));
    }

//    @Override
//    public String storeProduct(UploadRequest uploadRequest) {
//        String sqlCommand = "INSERT INTO warehouse (warehouse_no, bill_date, order_date, work_time, product_id, product_name, prod_name, " +
//                "valid_date, est_date, quantity, qj_set_weight, ware_in_class, ware_id, storage_id, order_no, pda_id, prod_id, " +
//                "batch_id, serial_no, pallet_no, created_date, last_modified_date) " +
//                "VALUE(:warehouseNo, :billDate, :orderDate, :workTime, :productId, :productName, :prodName, :validDate, :estDate, :quantity, :qjSetWeight, " +
//                ":wareInClass, :wareId, :storageId, :orderNo, :pdaId, :prodId, :batchId, :serialNo, :palletNo, :createdDate, :lastModifiedDate)";
//
//        List<UploadRequest.AllSerialNo> serialNoList = uploadRequest.getSerialNo().getAllSerialNoList();
//        UploadRequest.MasterData masterData = uploadRequest.getData().getMasterDataList().get(0);
//        List<UploadRequest.DetailData> detailDataList = masterData.getDetailDataList();
//        String warehouseNo = "WHID" + new Date().getTime();
//        Date now = new Date();
//
//        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[serialNoList.size()];
//
//        Map<String, UploadRequest.DetailData> map = new HashMap<>();
//        for (UploadRequest.DetailData detailData : detailDataList) {
//            map.put(detailData.getProdID(), detailData);
//        }
//        for (int i = 0; i < serialNoList.size(); i++) {
//            UploadRequest.AllSerialNo item = serialNoList.get(i);
//            UploadRequest.DetailData detailData = map.get(item.getProductId());
//            UploadRequest.DetailDataSub detailDataSub = detailData.getDetailDataSubList().get(0);
//
//            parameterSources[i] = new MapSqlParameterSource();
//            parameterSources[i].addValue("warehouseNo", warehouseNo);
//            parameterSources[i].addValue("billDate", masterData.getBillDate());
//            parameterSources[i].addValue("orderDate", masterData.getOrderDate());
//            parameterSources[i].addValue("workTime", masterData.getWorkTimeRealProc());
//            parameterSources[i].addValue("productId", masterData.getProductId());
//            parameterSources[i].addValue("productName", masterData.getProductName());
//            parameterSources[i].addValue("prodName", detailData.getProdName());
//            parameterSources[i].addValue("validDate", detailDataSub.getValidDate());
//            parameterSources[i].addValue("estDate", detailDataSub.getEstWareInDate());
//            parameterSources[i].addValue("quantity", detailDataSub.getQuantity());
//            parameterSources[i].addValue("qjSetWeight", detailDataSub.getQJSetWeight());
//            parameterSources[i].addValue("wareInClass", detailData.getWareInClass());
//            parameterSources[i].addValue("wareId", detailData.getWareID());
//            parameterSources[i].addValue("storageId", detailDataSub.getStorageID());
//            parameterSources[i].addValue("orderNo", item.getOrderNo());
//            parameterSources[i].addValue("pdaId", item.getPdaId());
//            parameterSources[i].addValue("prodId", item.getProductId());
//            parameterSources[i].addValue("batchId", item.getBatchId());
//            parameterSources[i].addValue("serialNo", item.getSerial());
//            parameterSources[i].addValue("palletNo", item.getPalletNo());
//            parameterSources[i].addValue("createdDate", now);
//            parameterSources[i].addValue("lastModifiedDate", now);
//        }
//        //一次執行大量SQL語法
//        namedParameterJdbcTemplate.batchUpdate(sqlCommand, parameterSources);
//
//        return warehouseNo;
//    }
}
