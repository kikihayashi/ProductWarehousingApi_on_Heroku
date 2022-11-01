package com.woody.productwarehousingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woody.productwarehousingapi.dto.BarcodeItem;
import com.woody.productwarehousingapi.dto.PalletItem;
import com.woody.productwarehousingapi.dto.PalletItemWithNo;
import com.woody.productwarehousingapi.dto.UploadRequest;
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
public class UploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PrintService printService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    @Test
    public void upload_success() throws Exception {
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

        PalletItemWithNo palletItemWithNoNew = printService.getPalletById(id);

        UploadRequest.DetailDataSub detailDataSub = new UploadRequest.DetailDataSub();
        detailDataSub.setParentRowNO("6");
        detailDataSub.setBatchID("20221019");
        detailDataSub.setQuantity("1");
        detailDataSub.setProduceDate("6");
        detailDataSub.setValidDate("20231031");
        detailDataSub.setEstWareInDate(20221031);
        detailDataSub.setStorageID("S1");
        detailDataSub.setSerial("11000004");
        detailDataSub.setQjSetWeight("0");
        detailDataSub.setMemo("");
        detailDataSub.setIsBox("1");
        detailDataSub.setCreated_at("2022-10-31 18:13:12");

        List<UploadRequest.DetailDataSub> detailDataSubList = new ArrayList<>();
        detailDataSubList.add(detailDataSub);

        UploadRequest.DetailData detailData = new UploadRequest.DetailData();
        detailData.setRowNO("6");
        detailData.setWareInClass("A1");
        detailData.setWareID("W1");
        detailData.setProdID("B2-02");
        detailData.setProdName("醬燒大排10片-6kg/箱-白2");
        detailData.setPrd_Quantity("1");
        detailData.setItemRemark("");
        detailData.setDetailDataSubList(detailDataSubList);

        List<UploadRequest.DetailData> detailDataList = new ArrayList<>();
        detailDataList.add(detailData);

        UploadRequest.MasterData masterData = new UploadRequest.MasterData();
        masterData.setBillDate("20221101");
        masterData.setOrderNo("20210104001");
        masterData.setWorkTimeRealProc("0.01");
        masterData.setOrderDate("20221031");
        masterData.setProductId("B2**");
        masterData.setProductName("(半成品-分級)棒腿-多品");
        masterData.setSrcNoInQty("0");
        masterData.setDetailDataList(detailDataList);

        List<UploadRequest.MasterData> masterDataList = new ArrayList<>();
        masterDataList.add(masterData);

        UploadRequest.Data data = new UploadRequest.Data();
        data.setMasterDataList(masterDataList);

        List<UploadRequest.AllSerialNo> allSerialNoList = new ArrayList<>();
        UploadRequest.AllSerialNo allSerialNo = new UploadRequest.AllSerialNo();
        allSerialNo.setOrderNo("20210104001");
        allSerialNo.setPdaId("25");
        allSerialNo.setProductId("B2-02");
        allSerialNo.setBatchId("20221019");
        allSerialNo.setSerial("11000004");
        allSerialNo.setQtyOrWeight("1");
        allSerialNo.setPalletNo(palletItemWithNoNew.getPalletNo());
        allSerialNoList.add(allSerialNo);

        UploadRequest.SerialNo serialNo = new UploadRequest.SerialNo();
        serialNo.setAllSerialNoList(allSerialNoList);

        UploadRequest uploadRequest = new UploadRequest();
        uploadRequest.setCompanyId("99");
        uploadRequest.setUserId("administrator");
        uploadRequest.setUserPassword("kikihayashi");
        uploadRequest.setPdaId("25");
        uploadRequest.setTag("Upload_Data");
        uploadRequest.setData(data);
        uploadRequest.setSerialNo(serialNo);

        String json = objectMapper.writeValueAsString(uploadRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.Result.IfSucceed", equalTo("True")))
                .andExpect(jsonPath("$.Result.ErrMessage", equalTo("")));
    }

    @Transactional
    @Test
    public void upload_palletIsUploaded() throws Exception {
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

        PalletItemWithNo palletItemWithNoNew = printService.getPalletById(id);

        UploadRequest.DetailDataSub detailDataSub = new UploadRequest.DetailDataSub();
        detailDataSub.setParentRowNO("6");
        detailDataSub.setBatchID("20221019");
        detailDataSub.setQuantity("1");
        detailDataSub.setProduceDate("6");
        detailDataSub.setValidDate("20231031");
        detailDataSub.setEstWareInDate(20221031);
        detailDataSub.setStorageID("S1");
        detailDataSub.setSerial("11000004");
        detailDataSub.setQjSetWeight("0");
        detailDataSub.setMemo("");
        detailDataSub.setIsBox("1");
        detailDataSub.setCreated_at("2022-10-31 18:13:12");

        List<UploadRequest.DetailDataSub> detailDataSubList = new ArrayList<>();
        detailDataSubList.add(detailDataSub);

        UploadRequest.DetailData detailData = new UploadRequest.DetailData();
        detailData.setRowNO("6");
        detailData.setWareInClass("A1");
        detailData.setWareID("W1");
        detailData.setProdID("B2-02");
        detailData.setProdName("醬燒大排10片-6kg/箱-白2");
        detailData.setPrd_Quantity("1");
        detailData.setItemRemark("");
        detailData.setDetailDataSubList(detailDataSubList);

        List<UploadRequest.DetailData> detailDataList = new ArrayList<>();
        detailDataList.add(detailData);

        UploadRequest.MasterData masterData = new UploadRequest.MasterData();
        masterData.setBillDate("20221101");
        masterData.setOrderNo("20210104001");
        masterData.setWorkTimeRealProc("0.01");
        masterData.setOrderDate("20221031");
        masterData.setProductId("B2**");
        masterData.setProductName("(半成品-分級)棒腿-多品");
        masterData.setSrcNoInQty("0");
        masterData.setDetailDataList(detailDataList);

        List<UploadRequest.MasterData> masterDataList = new ArrayList<>();
        masterDataList.add(masterData);

        UploadRequest.Data data = new UploadRequest.Data();
        data.setMasterDataList(masterDataList);

        List<UploadRequest.AllSerialNo> allSerialNoList = new ArrayList<>();
        UploadRequest.AllSerialNo allSerialNo = new UploadRequest.AllSerialNo();
        allSerialNo.setOrderNo("20210104001");
        allSerialNo.setPdaId("25");
        allSerialNo.setProductId("B2-02");
        allSerialNo.setBatchId("20221019");
        allSerialNo.setSerial("11000004");
        allSerialNo.setQtyOrWeight("1");
        allSerialNo.setPalletNo(palletItemWithNoNew.getPalletNo());
        allSerialNoList.add(allSerialNo);

        UploadRequest.SerialNo serialNo = new UploadRequest.SerialNo();
        serialNo.setAllSerialNoList(allSerialNoList);

        UploadRequest uploadRequest = new UploadRequest();
        uploadRequest.setCompanyId("99");
        uploadRequest.setUserId("administrator");
        uploadRequest.setUserPassword("kikihayashi");
        uploadRequest.setPdaId("25");
        uploadRequest.setTag("Upload_Data");
        uploadRequest.setData(data);
        uploadRequest.setSerialNo(serialNo);

        String json = objectMapper.writeValueAsString(uploadRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.Result.IfSucceed", equalTo("True")))
                .andExpect(jsonPath("$.Result.ErrMessage", equalTo("")));

        requestBuilder = MockMvcRequestBuilders
                .post("/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.Result.IfSucceed", equalTo("False")))
                .andExpect(jsonPath("$.Result.ErrMessage", equalTo("錯誤，棧板號：" + palletItemWithNoNew.getPalletNo() + "已上傳過！")));
    }
}