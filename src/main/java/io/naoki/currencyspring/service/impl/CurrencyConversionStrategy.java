package io.naoki.currencyspring.service.impl;

import io.naoki.currencyspring.entity.ExchangeRate;
import io.naoki.currencyspring.infrastructure.ExchangeRateUtils;
import io.naoki.currencyspring.repository.ExchangeRateRepository;
import io.naoki.currencyspring.service.ConversionStrategy;

import java.math.BigDecimal;
import java.util.Optional;


public abstract class CurrencyConversionStrategy implements ConversionStrategy {

    protected final ExchangeRateRepository exchangeRateRepository;

    protected CurrencyConversionStrategy(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public Optional<ExchangeRate> convert(String from, String to) {
        String commonCurrencyCode = getCommonCurrencyCode(from, to);

        var exchangeRateBaseOpt = exchangeRateRepository
                .findByPairCodesBidirectional(from, commonCurrencyCode);

        return exchangeRateBaseOpt.flatMap(exchangeRateBase -> {

            var exchangeRateTargetOpt = exchangeRateRepository
                    .findByPairCodesBidirectional(to, commonCurrencyCode);

            return exchangeRateTargetOpt.map(exchangeRateTarget -> getExchangeRateFromCommonCurrency(
                    exchangeRateBase, exchangeRateTarget, commonCurrencyCode));
        });
    }


    private ExchangeRate getExchangeRateFromCommonCurrency(ExchangeRate erBase, ExchangeRate erTarget, String commonCode) {
        ExchangeRate commonToBase = ExchangeRateUtils.rearrangeExchangeRateBy(commonCode, erBase, DIVIDING_CONTEXT);
        ExchangeRate commonToTarget = ExchangeRateUtils.rearrangeExchangeRateBy(commonCode, erTarget, DIVIDING_CONTEXT);
        return new ExchangeRate(
                null,
                commonToBase.targetCurrency(),
                commonToTarget.targetCurrency(),
                calculateRate(commonToTarget, commonToBase)
        );
    }

    private BigDecimal calculateRate(ExchangeRate commonToTarget, ExchangeRate commonToBase) {
        return commonToTarget.rate().divide(commonToBase.rate(), DIVIDING_CONTEXT);
    }

    protected abstract String getCommonCurrencyCode(String code1, String code2);

}
