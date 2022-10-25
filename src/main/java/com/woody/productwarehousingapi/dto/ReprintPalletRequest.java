package com.woody.productwarehousingapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class ReprintPalletRequest extends PalletRequest {

    @NotBlank
    @JsonProperty("PalletId")
    private String palletId;

    public String getPalletId() {
        return palletId;
    }

    public void setPalletId(String palletId) {
        this.palletId = palletId;
    }
}
