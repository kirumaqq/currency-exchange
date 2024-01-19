package io.naoki.currencyspring.currency;

public record CreateCurrencyDto(
        String name,
        String code,
        String sign
) {
}
