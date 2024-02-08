package io.naoki.currencyspring.dto.currency;

import io.naoki.currencyspring.infrastructure.ISO4217;

public record CurrencyPair(
        @ISO4217
        String baseCurrencyCode,
        @ISO4217
        String targetCurrencyCode
) {
}
