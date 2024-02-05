package io.naoki.currencyspring.dto.conversion;

import io.naoki.currencyspring.entity.Currency;

import java.math.BigDecimal;

public record ConversionResponse(
        Currency baseCurrency,
        Currency targetCurrency,
        BigDecimal rate,
        BigDecimal amount,
        BigDecimal convertedAmount
) {
}
