package io.naoki.currencyspring.exchangerate;

import io.naoki.currencyspring.currency.CurrencyResponseDto;

import java.math.BigDecimal;

public record ExchangeRateResponseDto(
        Integer id,
        CurrencyResponseDto baseCurrency,
        CurrencyResponseDto targetCurrency,
        BigDecimal rate
) {
}
