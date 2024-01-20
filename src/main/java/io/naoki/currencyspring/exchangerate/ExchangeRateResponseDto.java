package io.naoki.currencyspring.exchangerate;

import io.naoki.currencyspring.currency.CurrencyResponseDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record ExchangeRateResponseDto(
        Integer id,
        CurrencyResponseDto baseCurrency,
        CurrencyResponseDto targetCurrency,
        BigDecimal rate
) {

    public ExchangeRateResponseDto(Integer id, CurrencyResponseDto baseCurrency, CurrencyResponseDto targetCurrency, BigDecimal rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate.setScale(2, RoundingMode.HALF_EVEN);
    }
}
