package com.woody.productwarehousingapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InfoResponse {

    @JsonProperty("Message")
    private String message;
    @JsonProperty("Data")
    private Data data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @JsonProperty("Query1")
        private List<Query> queryList;

        public List<Query> getQueryList() {
            return queryList;
        }

        public void setQueryList(List<Query> queryList) {
            this.queryList = queryList;
        }
    }

    public static class Query {
        @JsonProperty("ProdID")
        private String productId;
        @JsonProperty("ProdName")
        private String productName;
        @JsonProperty("QJSetWeight")
        private String qjSetWeight;
        @JsonProperty("QJUppLimitWt")
        private String qjUppLimitWt;
        @JsonProperty("QJLowLimitWt")
        private String qjLowLimitWt;
        @JsonProperty("DefValidDays")
        private String defValidDays;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getQjSetWeight() {
            return qjSetWeight;
        }

        public void setQjSetWeight(String qjSetWeight) {
            this.qjSetWeight = qjSetWeight;
        }

        public String getQjUppLimitWt() {
            return qjUppLimitWt;
        }

        public void setQjUppLimitWt(String qjUppLimitWt) {
            this.qjUppLimitWt = qjUppLimitWt;
        }

        public String getQjLowLimitWt() {
            return qjLowLimitWt;
        }

        public void setQjLowLimitWt(String qjLowLimitWt) {
            this.qjLowLimitWt = qjLowLimitWt;
        }

        public String getDefValidDays() {
            return defValidDays;
        }

        public void setDefValidDays(String defValidDays) {
            this.defValidDays = defValidDays;
        }
    }
}
