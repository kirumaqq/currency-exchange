package io.naoki.currencyspring.dto.exchangerate;

import io.naoki.currencyspring.infrastructure.ISO4217;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateExchangeRateDto(
        @ISO4217
        String baseCurrencyCode,
        @ISO4217
        String targetCurrencyCode,
        @Positive
        BigDecimal rate
) {
        public CreateExchangeRateDto(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) {
                this.baseCurrencyCode = baseCurrencyCode.toUpperCase();
                this.targetCurrencyCode = targetCurrencyCode.toUpperCase();
                this.rate = rate;
        }
}
