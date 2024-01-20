package io.naoki.currencyspring.dto.currency;

public record CreateCurrencyDto(
        String name,
        String code,
        String sign
) {
}
