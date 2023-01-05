package com.woody.productwarehousingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woody.productwarehousingapi.dto.InfoRequest;
import com.woody.productwarehousingapi.dto.OrderRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.woody.productwarehousingapi.utils.SecurityTestUtils.setUser;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    // 查詢訂單
    @Test
    public void getOrder_success() throws Exception {

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCompanyId("99");
        orderRequest.setUserId("administrator");
        orderRequest.setUserPassword("chi");
        orderRequest.setTag("Order_Query");

        OrderRequest.Data data = new OrderRequest.Data();
        OrderRequest.MasterData masterData = new OrderRequest.MasterData();
        masterData.setOrderNo("20221025001");
        List<OrderRequest.MasterData> list = new ArrayList<>();
        list.add(masterData);
        data.setMasterDataList(list);
        orderRequest.setData(data);

        String json = objectMapper.writeValueAsString(orderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        requestBuilder = setUser("username", "password", "admin", "manager", requestBuilder);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.result[0].IfSucceed", equalTo("True")))
                .andExpect(jsonPath("$.result[0].ErrMessage", equalTo("")))
                .andExpect(jsonPath("$.result[0].IfBiz", equalTo("True")))
                .andExpect(jsonPath("$.MasterData[0].BillStatus", equalTo(0)))
                .andExpect(jsonPath("$.MasterData[0].MKOrdNO", equalTo("20221025001")))
                .andExpect(jsonPath("$.MasterData[0].MKOrdDate", equalTo("20221025")))
                .andExpect(jsonPath("$.MasterData[0].ProductID", equalTo("A2**")))
                .andExpect(jsonPath("$.MasterData[0].ProductName", equalTo("(半成品-分級)棒腿-多品")))
                .andExpect(jsonPath("$.MasterData[0].srcNoInQty", equalTo("0")))
                .andExpect(jsonPath("$.MasterData[0].EstWareInDate", equalTo("20221025")))
                .andExpect(jsonPath("$.MasterData[0].DetailData1[1].RowNo", equalTo(2)))
                .andExpect(jsonPath("$.MasterData[0].DetailData1[1].NCTProdID", equalTo("A2-02")));
    }


    @Test
    public void getOrder_notExistOrder() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCompanyId("99");
        orderRequest.setUserId("administrator");
        orderRequest.setUserPassword("chi");
        orderRequest.setTag("Order_Query");

        OrderRequest.Data data = new OrderRequest.Data();
        OrderRequest.MasterData masterData = new OrderRequest.MasterData();
        masterData.setOrderNo("99999999999");
        List<OrderRequest.MasterData> list = new ArrayList<>();
        list.add(masterData);
        data.setMasterDataList(list);
        orderRequest.setData(data);

        String json = objectMapper.writeValueAsString(orderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        requestBuilder = setUser("username", "password", "admin", "manager", requestBuilder);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.result[0].IfSucceed", equalTo("False")))
                .andExpect(jsonPath("$.result[0].ErrMessage", equalTo("無此製令單")))
                .andExpect(jsonPath("$.result[0].IfBiz", equalTo("False")));
    }

    @Test
    public void getInfo_success() throws Exception {

        List<InfoRequest.Query> list = new ArrayList<>();
        InfoRequest.Query query = new InfoRequest.Query();
        query.setProductId("A2-03");
        list.add(query);

        InfoRequest infoRequest = new InfoRequest();
        infoRequest.setCompany("99");
        infoRequest.setQueryList(list);

        String json = objectMapper.writeValueAsString(infoRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        requestBuilder = setUser("username", "password", "admin", "manager", requestBuilder);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.Message", equalTo("")))
                .andExpect(jsonPath("$.Data.Query1[0].ProdID", equalTo("A2-03")))
                .andExpect(jsonPath("$.Data.Query1[0].ProdName", equalTo("醬燒大排10片-6kg/箱-白3")))
                .andExpect(jsonPath("$.Data.Query1[0].QJSetWeight", equalTo("0")))
                .andExpect(jsonPath("$.Data.Query1[0].QJUppLimitWt", equalTo("0")))
                .andExpect(jsonPath("$.Data.Query1[0].QJLowLimitWt", equalTo("0")))
                .andExpect(jsonPath("$.Data.Query1[0].DefValidDays", equalTo("365")));
    }

    @Test
    public void getInfo_notExistProductId() throws Exception {

        List<InfoRequest.Query> list = new ArrayList<>();
        InfoRequest.Query query = new InfoRequest.Query();
        query.setProductId("XXX-99");
        list.add(query);

        InfoRequest infoRequest = new InfoRequest();
        infoRequest.setCompany("99");
        infoRequest.setQueryList(list);

        String json = objectMapper.writeValueAsString(infoRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        requestBuilder = setUser("username", "password", "admin", "manager", requestBuilder);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.Message", equalTo("失敗")));
    }


}