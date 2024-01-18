package io.naoki.currencyspring.exchangerate;

import io.naoki.currencyspring.currency.Currency;

import java.math.BigDecimal;

public record ExchangeRateResponseDto(
        Integer id,
        Currency baseCurrency,
        Currency targetCurrency,
        BigDecimal rate
) {
}
