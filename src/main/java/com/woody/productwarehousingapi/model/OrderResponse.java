package com.woody.productwarehousingapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//若回傳參數有Null則忽略該參數(不轉成json)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {

    @JsonProperty("result")
    private List<Result> resultList;
    @JsonProperty("MasterData")
    private List<MasterData> masterDataList;

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    public List<MasterData> getMasterDataList() {
        return masterDataList;
    }

    public void setMasterDataList(List<MasterData> masterDataList) {
        this.masterDataList = masterDataList;
    }

    public static class Result {

        @JsonProperty("IfSucceed")
        private String ifSucceed;
        @JsonProperty("ErrMessage")
        private String errMessage;
        @JsonProperty("IfBiz")
        private String ifBiz;

        public String getIfSucceed() {
            return ifSucceed;
        }

        public void setIfSucceed(String ifSucceed) {
            this.ifSucceed = ifSucceed;
        }

        public String getErrMessage() {
            return errMessage;
        }

        public void setErrMessage(String errMessage) {
            this.errMessage = errMessage;
        }

        public String getIfBiz() {
            return ifBiz;
        }

        public void setIfBiz(String ifBiz) {
            this.ifBiz = ifBiz;
        }
    }

    public static class MasterData {

        @JsonProperty("BillStatus")
        private Integer billStatus;
        @JsonProperty("MKOrdNO")
        private String orderNo;
        @JsonProperty("MKOrdDate")
        private Integer orderDate;
        @JsonProperty("ProductID")
        private String productID;
        @JsonProperty("ProductName")
        private String productName;
        @JsonProperty("srcNoInQty")
        private String srcNoInQty;
        @JsonProperty("EstWareInDate")
        private Integer estWareInDate;

        @JsonProperty("DetailData1")
        private List<DetailData1> detailData1List;

        public Integer getBillStatus() {
            return billStatus;
        }

        public void setBillStatus(Integer billStatus) {
            this.billStatus = billStatus;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public Integer getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(Integer orderDate) {
            this.orderDate = orderDate;
        }

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getSrcNoInQty() {
            return srcNoInQty;
        }

        public void setSrcNoInQty(String srcNoInQty) {
            this.srcNoInQty = srcNoInQty;
        }

        public Integer getEstWareInDate() {
            return estWareInDate;
        }

        public void setEstWareInDate(Integer estWareInDate) {
            this.estWareInDate = estWareInDate;
        }

        public List<DetailData1> getDetailData1List() {
            return detailData1List;
        }

        public void setDetailData1List(List<DetailData1> detailData1List) {
            this.detailData1List = detailData1List;
        }
    }

    public static class DetailData1 {
        @JsonProperty("RowNo")
        private Integer rowNo;
        @JsonProperty("NCTProdID")
        private String nctProductId;

        public Integer getRowNo() {
            return rowNo;
        }

        public void setRowNo(Integer rowNo) {
            this.rowNo = rowNo;
        }

        public String getNctProductId() {
            return nctProductId;
        }

        public void setNctProductId(String nctProductId) {
            this.nctProductId = nctProductId;
        }
    }

}
