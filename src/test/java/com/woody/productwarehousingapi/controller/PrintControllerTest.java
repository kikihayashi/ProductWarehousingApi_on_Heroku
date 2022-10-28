package com.woody.productwarehousingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woody.productwarehousingapi.dto.BarcodeItem;
import com.woody.productwarehousingapi.dto.InvalidPalletRequest;
import com.woody.productwarehousingapi.dto.PalletItem;
import com.woody.productwarehousingapi.dto.PalletItemWithNo;
import com.woody.productwarehousingapi.service.PrintService;
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

    @Autowired
    private PrintService printService;

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
                .andExpect(jsonPath("$.Qrcode", equalTo(barcodeItem.getQrcode())));
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

        printService.createBarcode(barcodeItem);

        String json = objectMapper.writeValueAsString(barcodeItem);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
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

        printService.createBarcode(barcodeItem);

        //設定不存在的Qrcode
        barcodeItem.setQrcode("1234567890");

        String json = objectMapper.writeValueAsString(barcodeItem);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/barcode/reprint")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(202))
                .andExpect(jsonPath("$.Message", equalTo("失敗，條碼:" + barcodeItem.getQrcode() + "不存在！")));
    }

    @Transactional
    @Test
    public void printPallet_success() throws Exception {
        BarcodeItem barcodeItem = new BarcodeItem();
        barcodeItem.setPrintIp("192.168.9.209");
        barcodeItem.setProductName("(半成品-分級)棒腿-多品");
        barcodeItem.setLotName("醬燒大排10片-6kg/箱-白2");
        barcodeItem.setQrcode("20210104001;20221019;B2-02;20221019;20231019;0;11000004;1");
        barcodeItem.setValidDay("365");
        barcodeItem.setWeightMax("0");
        barcodeItem.setWeightMin("0");

        printService.createBarcode(barcodeItem);

        PalletItem palletItem = new PalletItem();
        palletItem.setPrintIp("192.168.9.209");
        palletItem.setProductName("醬燒大排10片-6kg/箱-白2");
        palletItem.setProductId("B2-02");
        palletItem.setLotId("20221019");
        palletItem.setOrderId("20210104001");
        palletItem.setMakeDate("20221020");
        palletItem.setWeightSet("0");
        palletItem.setMachineId("25");

        String json = objectMapper.writeValueAsString(palletItem);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/pallet/print")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.Message", equalTo("成功")));
    }

    @Transactional
    @Test
    public void printPallet_barcodeCannotCollect() throws Exception {
        PalletItem palletItem = new PalletItem();
        palletItem.setPrintIp("192.168.9.209");
        palletItem.setProductName("醬燒大排10片-6kg/箱-白2");
        palletItem.setProductId("B2-02");
        palletItem.setLotId("20221019");
        palletItem.setOrderId("20210104001");
        palletItem.setMakeDate("20221020");
        palletItem.setWeightSet("0");
        palletItem.setMachineId("25");

        String json = objectMapper.writeValueAsString(palletItem);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/pallet/print")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.Message", equalTo("失敗，沒有符合條件的條碼可以綁定")));
    }

    @Transactional
    @Test
    public void reprintPallet_success() throws Exception {
        BarcodeItem barcodeItem = new BarcodeItem();
        barcodeItem.setPrintIp("192.168.9.209");
        barcodeItem.setProductName("(半成品-分級)棒腿-多品");
        barcodeItem.setLotName("醬燒大排10片-6kg/箱-白2");
        barcodeItem.setQrcode("20210104001;20221019;B2-02;20221019;20231019;0;11000004;1");
        barcodeItem.setValidDay("365");
        barcodeItem.setWeightMax("0");
        barcodeItem.setWeightMin("0");

        printService.createBarcode(barcodeItem);

        PalletItem palletItem = new PalletItem();
        palletItem.setPrintIp("192.168.9.209");
        palletItem.setProductName("醬燒大排10片-6kg/箱-白2");
        palletItem.setProductId("B2-02");
        palletItem.setLotId("20221019");
        palletItem.setOrderId("20210104001");
        palletItem.setMakeDate("20221020");
        palletItem.setWeightSet("0");
        palletItem.setMachineId("25");

        Integer id = printService.createPallet(palletItem);

        String palletNo = printService.getPalletById(id).getPalletNo();

        PalletItemWithNo palletItemWithNo = new PalletItemWithNo();
        palletItemWithNo.setPrintIp(palletItem.getPrintIp());
        palletItemWithNo.setProductName(palletItem.getProductName());
        palletItemWithNo.setProductId(palletItem.getProductId());
        palletItemWithNo.setLotId(palletItem.getLotId());
        palletItemWithNo.setOrderId(palletItem.getOrderId());
        palletItemWithNo.setMakeDate(palletItem.getMakeDate());
        palletItemWithNo.setWeightSet(palletItem.getWeightSet());
        palletItemWithNo.setMachineId(palletItem.getMachineId());
        palletItemWithNo.setPalletNo(palletNo);

        String json = objectMapper.writeValueAsString(palletItemWithNo);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/pallet/reprint")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(202))
                .andExpect(jsonPath("$.Message", equalTo("成功")))
                .andExpect(jsonPath("$.Qrcode", equalTo(palletNo)));

    }

    @Transactional
    @Test
    public void reprintPallet_palletIsNotExist() throws Exception {
        BarcodeItem barcodeItem = new BarcodeItem();
        barcodeItem.setPrintIp("192.168.9.209");
        barcodeItem.setProductName("(半成品-分級)棒腿-多品");
        barcodeItem.setLotName("醬燒大排10片-6kg/箱-白2");
        barcodeItem.setQrcode("20210104001;20221019;B2-02;20221019;20231019;0;11000004;1");
        barcodeItem.setValidDay("365");
        barcodeItem.setWeightMax("0");
        barcodeItem.setWeightMin("0");

        printService.createBarcode(barcodeItem);

        PalletItem palletItem = new PalletItem();
        palletItem.setPrintIp("192.168.9.209");
        palletItem.setProductName("醬燒大排10片-6kg/箱-白2");
        palletItem.setProductId("B2-02");
        palletItem.setLotId("20221019");
        palletItem.setOrderId("20210104001");
        palletItem.setMakeDate("20221020");
        palletItem.setWeightSet("0");
        palletItem.setMachineId("25");

        Integer id = printService.createPallet(palletItem);

        String palletNo = "ABC123456";

        PalletItemWithNo palletItemWithNo = new PalletItemWithNo();
        palletItemWithNo.setPrintIp(palletItem.getPrintIp());
        palletItemWithNo.setProductName(palletItem.getProductName());
        palletItemWithNo.setProductId(palletItem.getProductId());
        palletItemWithNo.setLotId(palletItem.getLotId());
        palletItemWithNo.setOrderId(palletItem.getOrderId());
        palletItemWithNo.setMakeDate(palletItem.getMakeDate());
        palletItemWithNo.setWeightSet(palletItem.getWeightSet());
        palletItemWithNo.setMachineId(palletItem.getMachineId());
        palletItemWithNo.setPalletNo(palletNo);

        String json = objectMapper.writeValueAsString(palletItemWithNo);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/pallet/reprint")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(202))
                .andExpect(jsonPath("$.Message", equalTo("失敗，棧板號:" + palletItemWithNo.getPalletNo() + "不存在")));
    }


    @Transactional
    @Test
    public void invalidPallet_success() throws Exception {
        BarcodeItem barcodeItem = new BarcodeItem();
        barcodeItem.setPrintIp("192.168.9.209");
        barcodeItem.setProductName("(半成品-分級)棒腿-多品");
        barcodeItem.setLotName("醬燒大排10片-6kg/箱-白2");
        barcodeItem.setQrcode("20210104001;20221019;B2-02;20221019;20231019;0;11000004;1");
        barcodeItem.setValidDay("365");
        barcodeItem.setWeightMax("0");
        barcodeItem.setWeightMin("0");

        printService.createBarcode(barcodeItem);

        InvalidPalletRequest invalidPalletRequest = new InvalidPalletRequest();
        invalidPalletRequest.setCompany("99");
        List<InvalidPalletRequest.SerialQuery> list = new ArrayList<>();
        InvalidPalletRequest.SerialQuery query = new InvalidPalletRequest.SerialQuery();
        query.setOrderId("20210104001");
        query.setProductId("B2-02");
        query.setLotId("20221019");
        query.setSerialId("11000004");
        list.add(query);
        invalidPalletRequest.setSerialQueryList(list);

        String json = objectMapper.writeValueAsString(invalidPalletRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/pallet/invalid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(202))
                .andExpect(jsonPath("$.Message", equalTo("成功")));
    }

}