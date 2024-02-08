package io.naoki.currencyspring.infrastructure;

import io.naoki.currencyspring.entity.ExchangeRate;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.MathContext;

@UtilityClass
public class ExchangeRateUtils {


    public ExchangeRate rearrangeExchangeRateBy(String baseCurrencyCode, ExchangeRate exchangeRate, MathContext mathContext) {
        return isReversedExchangeRate(baseCurrencyCode, exchangeRate) ?
                reverseExchangeRate(exchangeRate, mathContext) :
                exchangeRate;
    }

    private boolean isReversedExchangeRate(String baseCurrencyCode, ExchangeRate exchangeRate) {
        return !baseCurrencyCode.equals(exchangeRate.baseCurrency().code());
    }

    private ExchangeRate reverseExchangeRate(ExchangeRate exchangeRate, MathContext mathContext) {
        return new ExchangeRate(
                null,
                exchangeRate.targetCurrency(),
                exchangeRate.baseCurrency(),
                reverseBigDecimal(exchangeRate.rate(), mathContext)
        );
    }

    private BigDecimal reverseBigDecimal(BigDecimal bigDecimal, MathContext mathContext) {
        return bigDecimal.pow(-1, mathContext);
    }
}
