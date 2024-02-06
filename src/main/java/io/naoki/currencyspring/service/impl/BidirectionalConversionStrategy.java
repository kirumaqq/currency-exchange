package io.naoki.currencyspring.service.impl;

import io.naoki.currencyspring.entity.ExchangeRate;
import io.naoki.currencyspring.repository.ExchangeRateRepository;
import io.naoki.currencyspring.service.ConversionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BidirectionalConversionStrategy implements ConversionStrategy {

    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    public Optional<ExchangeRate> convert(String from, String to) {
        var exchangeRateOpt = exchangeRateRepository.findByPairCodesBidirectional(from, to);

        return exchangeRateOpt.map(exchangeRate -> rearrangeExchangeRateBy(from, exchangeRate));
    }

    private ExchangeRate rearrangeExchangeRateBy(String baseCurrencyCode, ExchangeRate exchangeRate) {
        return isReversedExchangeRate(baseCurrencyCode, exchangeRate) ?
                reverseExchangeRate(exchangeRate) :
                exchangeRate;
    }

    private boolean isReversedExchangeRate(String baseCurrencyCode, ExchangeRate exchangeRate) {
        return !baseCurrencyCode.equals(exchangeRate.baseCurrency().code());
    }

    private ExchangeRate reverseExchangeRate(ExchangeRate exchangeRate) {
        return new ExchangeRate(
                null,
                exchangeRate.targetCurrency(),
                exchangeRate.baseCurrency(),
                reverseBigDecimal(exchangeRate.rate())
        );
    }

    private BigDecimal reverseBigDecimal(BigDecimal bigDecimal) {
        return bigDecimal.pow(-1, DIVIDING_CONTEXT);
    }
}
