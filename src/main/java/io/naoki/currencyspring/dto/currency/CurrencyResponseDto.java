package io.naoki.currencyspring.dto.currency;

public record CurrencyResponseDto(
        Integer id,
        String name,
        String code,
        String sign
) {
}
