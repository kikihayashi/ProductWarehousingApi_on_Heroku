package com.woody.productwarehousingapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

//若回傳參數有Null則忽略該參數(不轉成json)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrintResponse {
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Qrcode")
    private String qrcode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
