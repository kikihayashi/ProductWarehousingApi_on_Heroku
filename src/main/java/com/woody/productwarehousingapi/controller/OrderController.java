package com.woody.productwarehousingapi.controller;

import com.woody.productwarehousingapi.dto.InfoRequest;
import com.woody.productwarehousingapi.dto.OrderRequest;
import com.woody.productwarehousingapi.model.InfoResponse;
import com.woody.productwarehousingapi.model.OrderResponse;
import com.woody.productwarehousingapi.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@Api(tags = "查詢功能")
//@RequestMapping(path = "/search")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("取得成品編號")
    @PostMapping("/order")
    public ResponseEntity<OrderResponse> getOrder(@RequestBody @Valid OrderRequest orderRequest) {
        String orderNo = orderRequest.getData().getMasterDataList().get(0).getOrderNo();

        OrderResponse orderResponse = orderService.getOrder(orderNo);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }


    /**
     * 使用PreAuthorize前，需再SecurityConfiguration中加上
     * EnableWebSecurity、EnableGlobalMethodSecurity(prePostEnabled = true)這兩個註釋
     * */
    @ApiOperation("取得成品詳細資訊")
    @PostMapping("/info")
    public ResponseEntity<InfoResponse> getInfo(@RequestBody @Valid InfoRequest infoRequest) {
        List<InfoRequest.Query> productIdList = infoRequest.getQueryList();

        InfoResponse infoResponse  = orderService.getInfo(productIdList);

        return ResponseEntity.status(HttpStatus.OK).body(infoResponse);
    }
}
