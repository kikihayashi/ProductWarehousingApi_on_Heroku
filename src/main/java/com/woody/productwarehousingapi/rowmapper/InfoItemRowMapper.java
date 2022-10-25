package com.woody.productwarehousingapi.rowmapper;

import com.woody.productwarehousingapi.dto.InfoItem;
import com.woody.productwarehousingapi.dto.OrderItem;
import com.woody.productwarehousingapi.model.InfoResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoItemRowMapper implements RowMapper<InfoItem> {
    @Override
    public InfoItem mapRow(ResultSet resultSet, int i) throws SQLException {
        InfoItem infoItem = new InfoItem();
//        infoItem.setOrderNo(resultSet.getString("order_no"));
        infoItem.setRowNo(resultSet.getString("row_no"));
        infoItem.setProductId(resultSet.getString("nct_product_id"));
        infoItem.setProductName(resultSet.getString("nct_product_name"));
        infoItem.setQjSetWeight(resultSet.getString("qj_set_weight"));
        infoItem.setQjUppLimitWt(resultSet.getString("qj_upp_weight"));
        infoItem.setQjLowLimitWt(resultSet.getString("qj_low_weight"));
        infoItem.setDefValidDays(resultSet.getString("def_valid_day"));
//        infoItem.setCreatedDate(resultSet.getTimestamp("created_date"));
//        infoItem.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return infoItem;
    }
}
