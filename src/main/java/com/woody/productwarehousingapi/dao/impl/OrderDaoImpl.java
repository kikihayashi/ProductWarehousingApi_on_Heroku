package com.woody.productwarehousingapi.dao.impl;

import com.woody.productwarehousingapi.dao.OrderDao;
import com.woody.productwarehousingapi.dto.InfoItem;
import com.woody.productwarehousingapi.dto.OrderItem;
import com.woody.productwarehousingapi.rowmapper.InfoItemRowMapper;
import com.woody.productwarehousingapi.rowmapper.OrderItemRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<OrderItem> getOrder(String orderNo) {
        String sqlCommand = "SELECT ol.bill_status, ol.order_no, ol.order_date, ol.product_id," +
                "ol.product_name, ol.src_qty, ol.est_date ,il.row_no, il.nct_product_id " +
                "FROM order_list AS ol " +
                "LEFT JOIN info_list AS il ON ol.order_no = il.order_no " +
                "WHERE ol.order_no = :orderNo";

        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", orderNo);

        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sqlCommand, map, new OrderItemRowMapper());

        return orderItemList;
    }

    @Override
    public InfoItem getInfoByProductId(String productId) {
        String sqlCommand = "SELECT row_no, nct_product_id, nct_product_name, " +
                "qj_set_weight, qj_upp_weight, qj_low_weight, def_valid_day " +
                "FROM info_list " +
                "WHERE nct_product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<InfoItem> infoItemList = namedParameterJdbcTemplate.query(sqlCommand, map, new InfoItemRowMapper());

        return (infoItemList.size() > 0) ? infoItemList.get(0) : null;
    }
}
