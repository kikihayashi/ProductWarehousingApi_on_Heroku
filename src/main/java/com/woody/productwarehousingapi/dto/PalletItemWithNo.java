package com.woody.productwarehousingapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class PalletItemWithNo extends PalletItem {

    @NotBlank
    @JsonProperty("PalletId")
    private String palletNo;

    public String getPalletNo() {
        return palletNo;
    }

    public void setPalletNo(String palletNo) {
        this.palletNo = palletNo;
    }
}
