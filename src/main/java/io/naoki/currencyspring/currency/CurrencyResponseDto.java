package io.naoki.currencyspring.currency;

public record CurrencyResponseDto(
        Integer id,
        String name,
        String code,
        String sign
) {
}
