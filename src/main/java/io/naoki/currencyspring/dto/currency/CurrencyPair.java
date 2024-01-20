package io.naoki.currencyspring.dto.currency;

public record CurrencyPair(
        String baseCurrencyCode,
        String targetCurrencyCode
) {
}
