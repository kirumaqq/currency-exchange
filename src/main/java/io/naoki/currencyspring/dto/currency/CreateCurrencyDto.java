package io.naoki.currencyspring.dto.currency;

import io.naoki.currencyspring.infrastructure.ISO4217;
import jakarta.validation.constraints.Size;

public record CreateCurrencyDto(
        @Size(min = 3, max = 40, message = "Currency name length must not be less than 3 and greater than 40")
        String name,
        @ISO4217
        String code,
        @Size(min = 1, max = 10, message = "Currency name length must not be less than 1 and greater than 10")
        String sign
) {
        public CreateCurrencyDto(String name, String code, String sign) {
                this.name = name;
                this.code = code.toUpperCase();
                this.sign = sign;
        }
}
