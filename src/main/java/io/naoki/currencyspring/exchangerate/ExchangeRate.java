package io.naoki.currencyspring.exchangerate;

import io.naoki.currencyspring.currency.Currency;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("exchange_rates")
public record ExchangeRate(
        Integer id,
        @Column("base_currency_id")
        Currency baseCurrency,
        @Column("target_currency_id")
        Currency targetCurrency,
        BigDecimal rate

) {
}
