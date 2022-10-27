package com.woody.productwarehousingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woody.productwarehousingapi.dto.BarcodeItem;
import com.woody.productwarehousingapi.dto.OrderRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PrintControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    @Test
    public void printBarcode_success() throws Exception {
        BarcodeItem barcodeItem = new BarcodeItem();
        barcodeItem.setPrintIp("192.168.9.209");
        barcodeItem.setProductName("(半成品-分級)棒腿-多品");
        barcodeItem.setLotName("醬燒大排10片-6kg/箱-白2");
        barcodeItem.setQrcode("20210104001;20221019;B2-02;20221019;20231019;0;11000004;1");
        barcodeItem.setValidDay("365");
        barcodeItem.setWeightMax("0");
        barcodeItem.setWeightMin("0");

        String json = objectMapper.writeValueAsString(barcodeItem);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/barcode/print")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.Message", equalTo("成功")))
                .andExpect(jsonPath("$.Qrcode", equalTo("20210104001;20221019;B2-02;20221019;20231019;0;11000004;1")));
    }

    @Transactional
    @Test
    public void printBarcode_barcodeIsExist() throws Exception {
        BarcodeItem barcodeItem = new BarcodeItem();
        barcodeItem.setPrintIp("192.168.9.209");
        barcodeItem.setProductName("(半成品-分級)棒腿-多品");
        barcodeItem.setLotName("醬燒大排10片-6kg/箱-白2");
        barcodeItem.setQrcode("20210104001;20221019;B2-02;20221019;20231019;0;11000004;1");
        barcodeItem.setValidDay("365");
        barcodeItem.setWeightMax("0");
        barcodeItem.setWeightMin("0");

        String json = objectMapper.writeValueAsString(barcodeItem);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/barcode/print")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.Message", equalTo("成功")))
                .andExpect(jsonPath("$.Qrcode", equalTo(barcodeItem.getQrcode())));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.Message", equalTo("失敗，條碼:" + barcodeItem.getQrcode() + "已被使用")));
    }

    @Transactional
    @Test
    public void reprintBarcode_success() throws Exception {
        BarcodeItem barcodeItem = new BarcodeItem();
        barcodeItem.setPrintIp("192.168.9.209");
        barcodeItem.setProductName("(半成品-分級)棒腿-多品");
        barcodeItem.setLotName("醬燒大排10片-6kg/箱-白2");
        barcodeItem.setQrcode("20210104001;20221019;B2-02;20221019;20231019;0;11000004;1");
        barcodeItem.setValidDay("365");
        barcodeItem.setWeightMax("0");
        barcodeItem.setWeightMin("0");

        String json = objectMapper.writeValueAsString(barcodeItem);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/barcode/print")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.Message", equalTo("成功")))
                .andExpect(jsonPath("$.Qrcode", equalTo(barcodeItem.getQrcode())));

        requestBuilder = MockMvcRequestBuilders
                .post("/barcode/reprint")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(202))
                .andExpect(jsonPath("$.Message", equalTo("成功")))
                .andExpect(jsonPath("$.Qrcode", equalTo(barcodeItem.getQrcode())));


    }

    @Transactional
    @Test
    public void reprintBarcode_barcodeIsNotExist() throws Exception {
        BarcodeItem barcodeItem = new BarcodeItem();
        barcodeItem.setPrintIp("192.168.9.209");
        barcodeItem.setProductName("(半成品-分級)棒腿-多品");
        barcodeItem.setLotName("醬燒大排10片-6kg/箱-白2");
        barcodeItem.setQrcode("20210104001;20221019;B2-02;20221019;20231019;0;11000004;1");
        barcodeItem.setValidDay("365");
        barcodeItem.setWeightMax("0");
        barcodeItem.setWeightMin("0");

        String json = objectMapper.writeValueAsString(barcodeItem);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/barcode/print")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.Message", equalTo("成功")))
                .andExpect(jsonPath("$.Qrcode", equalTo(barcodeItem.getQrcode())));

        //設定不存在的Qrcode
        barcodeItem.setQrcode("1234567890");

        json = objectMapper.writeValueAsString(barcodeItem);

        requestBuilder = MockMvcRequestBuilders
                .post("/barcode/reprint")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(202))
                .andExpect(jsonPath("$.Message", equalTo("失敗，條碼:" + barcodeItem.getQrcode() + "不存在！")));
    }


}