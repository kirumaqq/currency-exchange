package io.naoki.currencyspring.mapper;

import io.naoki.currencyspring.entity.Currency;
import io.naoki.currencyspring.entity.ExchangeRate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeRateRowMapper implements RowMapper<ExchangeRate> {
    @Override
    public ExchangeRate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ExchangeRate(rs.getInt("id"),
                new Currency(rs.getInt("base_id"),
                        rs.getString("base_code"),
                        rs.getString("base_name"),
                        rs.getString("base_sign")),
                new Currency(rs.getInt("target_id"),
                        rs.getString("target_code"),
                        rs.getString("target_name"),
                        rs.getString("target_sign")
                ),
                rs.getBigDecimal("rate"));
    }
}
