package com.woody.productwarehousingapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {
    @NotBlank
    @JsonProperty("CID")
    private String companyId;
    @NotBlank
    @JsonProperty("UID")
    private String userId;
    @NotBlank
    @JsonProperty("UPWD")
    private String userPassword;
    @NotBlank
    @JsonProperty("Tag")
    private String tag;
    @NotNull
    @JsonProperty("Data")
    private Data data;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @NotEmpty
        @JsonProperty("MasterData")
        private List<MasterData> masterDataList;

        public List<MasterData> getMasterDataList() {
            return masterDataList;
        }

        public void setMasterDataList(List<MasterData> masterDataList) {
            this.masterDataList = masterDataList;
        }
    }


    public static class MasterData {
        @NotBlank
        @JsonProperty("MKOrdNO")
        private String orderNo;

        public String getOrderNo() {

            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }
    }


}
