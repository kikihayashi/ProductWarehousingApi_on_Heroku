package com.woody.productwarehousingapi.dao.impl;

import com.woody.productwarehousingapi.dao.UploadDao;
import com.woody.productwarehousingapi.dto.UploadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UploadDaoImpl implements UploadDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String storeProduct(UploadRequest uploadRequest) {
        String sqlCommand = "INSERT INTO serial_list (warehouse_no, order_no, prod_id, prod_name, batch_id, serial_no, pallet_no, " +
                "valid_date, est_date, qj_set_weight, pda_id, ware_in_class, ware_id, storage_id, created_date, last_modified_date) " +
                "VALUES(:warehouseNo, :orderNo, :prodId, :prodName, :batchId, :serialNo, :palletNo, :validDate, :estDate, :qjSetWeight, " +
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
            parameterSources[i].addValue("qjSetWeight", detailDataSub.getQjSetWeight());
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

    @Override
    public boolean checkIfPalletUploaded(String pallet) {
        String sqlCommand = "SELECT COUNT(1) FROM pallet_list WHERE pallet_no = :pallet AND upload_status = :uploadStatus";

        Map<String, Object> map = new HashMap<>();
        map.put("pallet", pallet);
        map.put("uploadStatus", "Y");

        Integer count = namedParameterJdbcTemplate.queryForObject(sqlCommand, map, Integer.class);

        return count > 0;
    }

    @Override
    public void changePalletUploadStatus(Set<String> palletSet) {
        String sqlCommand = "UPDATE pallet_list SET upload_status = :uploadStatus WHERE pallet_no = :palletNo";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[palletSet.size()];
        int i = 0;
        for (String pallet : palletSet) {
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("uploadStatus", "Y");
            parameterSources[i].addValue("palletNo", pallet);
            i++;
        }
        //一次執行大量SQL語法
        namedParameterJdbcTemplate.batchUpdate(sqlCommand, parameterSources);
    }
}
