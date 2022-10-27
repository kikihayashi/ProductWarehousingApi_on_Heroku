package com.woody.productwarehousingapi.service.impl;

import com.woody.productwarehousingapi.dao.OrderDao;
import com.woody.productwarehousingapi.dto.InfoItem;
import com.woody.productwarehousingapi.dto.InfoRequest;
import com.woody.productwarehousingapi.dto.OrderItem;
import com.woody.productwarehousingapi.model.InfoResponse;
import com.woody.productwarehousingapi.model.OrderResponse;
import com.woody.productwarehousingapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public OrderResponse getOrder(String orderNo) {
        List<OrderItem> orderList = orderDao.getOrder(orderNo);
        OrderResponse orderResponse = new OrderResponse();
        OrderResponse.Result result = new OrderResponse.Result();

        if (orderList.size() > 0) {
            result.setErrMessage("");
            result.setIfBiz("True");
            result.setIfSucceed("True");

            OrderResponse.MasterData masterData = new OrderResponse.MasterData();
            masterData.setOrderNo(orderList.get(0).getOrderNo());
            masterData.setOrderDate(orderList.get(0).getOrderDate());
            masterData.setProductID(orderList.get(0).getProductID());
            masterData.setProductName(orderList.get(0).getProductName());
            masterData.setEstWareInDate(orderList.get(0).getEstWareInDate());
            masterData.setSrcNoInQty(orderList.get(0).getSrcNoInQty());
            masterData.setBillStatus(orderList.get(0).getBillStatus());

            List<OrderResponse.DetailData1> list = new ArrayList<>();
            for (OrderItem orderItem : orderList) {
                OrderResponse.DetailData1 detailData1 = new OrderResponse.DetailData1();
                detailData1.setNctProductId(orderItem.getNctProductId());
                detailData1.setRowNo(orderItem.getRowNo());
                list.add(detailData1);
            }
            masterData.setDetailData1List(list);
            List<OrderResponse.MasterData> masterDataList = new ArrayList<>();
            masterDataList.add(masterData);
            orderResponse.setMasterDataList(masterDataList);
        } else {
            result.setErrMessage("無此製令單");
            result.setIfBiz("False");
            result.setIfSucceed("False");
        }
        List<OrderResponse.Result> resultList = new ArrayList<>();
        resultList.add(result);
        orderResponse.setResultList(resultList);

        return orderResponse;
    }

    @Override
    public InfoResponse getInfo(List<InfoRequest.Query> productIdList) {
        InfoResponse infoResponse = new InfoResponse();
        List<InfoResponse.Query> infoList = new ArrayList<>();

        for (InfoRequest.Query q : productIdList) {
            String productId = q.getProductId();
            InfoItem infoItem = orderDao.getInfoByProductId(productId);
            if (infoItem == null) {
                infoResponse.setMessage("失敗");
                return infoResponse;
            }
            infoList.add(infoItem);
        }

        InfoResponse.Data data = new InfoResponse.Data();
        data.setQueryList(infoList);
        infoResponse.setMessage("");
        infoResponse.setData(data);
        return infoResponse;
    }
}
