package com.woody.productwarehousingapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InvalidPalletRequest {

    @JsonProperty("company")
    private String company;
    @JsonProperty("SerialQuery")
    private List<SerialQuery> serialQueryList;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<SerialQuery> getSerialQueryList() {
        return serialQueryList;
    }

    public void setSerialQueryList(List<SerialQuery> list) {
        this.serialQueryList = list;
    }

    //要static
    public static class SerialQuery {

        @JsonProperty("Order_ID")
        private String orderId;
        @JsonProperty("Pro_ID")
        private String productId;
        @JsonProperty("Lot_ID")
        private String lotId;
        @JsonProperty("Serial_ID")
        private String serialId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
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

        public String getSerialId() {
            return serialId;
        }

        public void setSerialId(String serialId) {
            this.serialId = serialId;
        }
    }

}
