package io.naoki.currencyspring.exchangerate;

import java.math.BigDecimal;

public record CreateExchangeRateDto(
        String baseCurrencyCode,
        String targetCurrencyCode,
        BigDecimal rate
) {
}
