package com.woody.productwarehousingapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class BarcodeItem {
    @NotEmpty
    @JsonProperty("Print_IP")
    private String printIp;
    @NotEmpty
    @JsonProperty("Pro_Name")
    private String productName;
    @NotEmpty
    @JsonProperty("Lot_Name")
    private String lotName;
    @NotEmpty
    @JsonProperty("QRcode")
    private String qrcode;
    @Min(1)
    @JsonProperty("Vaily_Day")
    private String validDay;
    @Min(0)
    @JsonProperty("Weight_Max")
    private String weightMax;
    @Min(0)
    @JsonProperty("Weight_Min")
    private String weightMin;

    public String getPrintIp() {
        return printIp;
    }

    public void setPrintIp(String printIp) {
        this.printIp = printIp;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getValidDay() {
        return validDay;
    }

    public void setValidDay(String validDay) {
        this.validDay = validDay;
    }

    public String getWeightMax() {
        return weightMax;
    }

    public void setWeightMax(String weightMax) {
        this.weightMax = weightMax;
    }

    public String getWeightMin() {
        return weightMin;
    }

    public void setWeightMin(String weightMin) {
        this.weightMin = weightMin;
    }
}
