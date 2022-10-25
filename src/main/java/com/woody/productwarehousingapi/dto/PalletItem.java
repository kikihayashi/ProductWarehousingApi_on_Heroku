package com.woody.productwarehousingapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PalletItem {
    @NotBlank
    @JsonProperty("Print_IP")
    private String printIp;
    @NotBlank
    @JsonProperty("Pro_Name")
    private String productName;
    @NotBlank
    @JsonProperty("Pro_Id")
    private String productId;
    @NotBlank
    @JsonProperty("Lot_Id")
    private String lotId;
    @NotBlank
    @JsonProperty("OrderId")
    private String orderId;
    @NotBlank
    @JsonProperty("Make_Date")
    private String makeDate;
    @Min(0)
    @JsonProperty("Weight_Set")
    private String weightSet;
    @NotBlank
    @JsonProperty("MachineId")
    private String machineId;

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(String makeDate) {
        this.makeDate = makeDate;
    }

    public String getWeightSet() {
        return weightSet;
    }

    public void setWeightSet(String weightSet) {
        this.weightSet = weightSet;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }
}
