package com.woody.productwarehousingapi.dto;

import com.woody.productwarehousingapi.model.OrderResponse;

import java.util.Date;

public class OrderItem extends OrderResponse.MasterData {

    private Integer rowNo;
    private String nctProductId;

    private Date createdDate;

    private Date lastModifiedDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

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
