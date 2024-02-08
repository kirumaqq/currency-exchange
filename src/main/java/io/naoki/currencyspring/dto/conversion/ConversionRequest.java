package io.naoki.currencyspring.dto.conversion;

import io.naoki.currencyspring.infrastructure.ISO4217;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ConversionRequest(
        @ISO4217
        String from,
        @ISO4217
        String to,
        @Positive
        BigDecimal amount
) {

        public ConversionRequest(String from, String to, BigDecimal amount) {
                this.from = from.toUpperCase();
                this.to = to.toUpperCase();
                this.amount = amount;
        }
}
