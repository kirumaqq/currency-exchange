package io.naoki.currencyspring.currency;

public record CurrencyPair(
        String baseCurrencyCode,
        String targetCurrencyCode
) {
}
