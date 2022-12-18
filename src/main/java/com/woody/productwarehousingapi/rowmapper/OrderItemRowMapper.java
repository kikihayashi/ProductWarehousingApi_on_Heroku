package com.woody.productwarehousingapi.rowmapper;

import com.woody.productwarehousingapi.dto.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {

        OrderItem orderItem = new OrderItem();
        orderItem.setBillStatus(resultSet.getInt("bill_status"));
        orderItem.setOrderNo(resultSet.getString("order_no"));
        orderItem.setOrderDate(resultSet.getDate("order_date"));
        orderItem.setProductID(resultSet.getString("product_id"));
        orderItem.setProductName(resultSet.getString("product_name"));
        orderItem.setSrcNoInQty(resultSet.getString("src_qty"));
        orderItem.setEstWareInDate(resultSet.getDate("est_date"));
        orderItem.setRowNo(resultSet.getInt("row_no"));
        orderItem.setNctProductId(resultSet.getString("nct_product_id"));
//        orderItem.setCreatedDate(resultSet.getTimestamp("created_date"));
//        orderItem.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return orderItem;
    }
}
