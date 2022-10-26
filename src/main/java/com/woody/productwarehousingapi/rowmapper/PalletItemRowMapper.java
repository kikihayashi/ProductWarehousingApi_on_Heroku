package com.woody.productwarehousingapi.rowmapper;

import com.woody.productwarehousingapi.dto.PalletItemWithNo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PalletItemRowMapper implements RowMapper<PalletItemWithNo> {
    @Override
    public PalletItemWithNo mapRow(ResultSet resultSet, int i) throws SQLException {
        PalletItemWithNo palletItemWithNo = new PalletItemWithNo();

        palletItemWithNo.setPalletNo(resultSet.getString("pallet_no"));
        palletItemWithNo.setWeightSet(resultSet.getString("weight_set"));
        palletItemWithNo.setMakeDate(resultSet.getString("make_date"));
        return palletItemWithNo;
    }
}
