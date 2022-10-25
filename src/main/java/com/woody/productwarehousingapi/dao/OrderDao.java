package com.woody.productwarehousingapi.dao;

import com.woody.productwarehousingapi.dto.InfoItem;
import com.woody.productwarehousingapi.dto.OrderItem;
import com.woody.productwarehousingapi.model.InfoResponse;

import java.util.List;

public interface OrderDao {
    List<OrderItem> getOrder(String orderNo);

    InfoItem getInfoByProductId(String productId);
}
