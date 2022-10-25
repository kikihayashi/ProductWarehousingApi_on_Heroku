package com.woody.productwarehousingapi.service;

import com.woody.productwarehousingapi.dto.InfoRequest;
import com.woody.productwarehousingapi.model.InfoResponse;
import com.woody.productwarehousingapi.model.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse getOrder(String orderNo);

    InfoResponse getInfo(List<InfoRequest.Query> productIdList);
}
