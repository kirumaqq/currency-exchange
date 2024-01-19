package io.naoki.currencyspring.exchangerate;

import io.naoki.currencyspring.currency.Currency;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("exchange_rates")
public record ExchangeRate(
        Integer id,
        Currency baseCurrency,
        Currency targetCurrency,
        BigDecimal rate

) {
}
