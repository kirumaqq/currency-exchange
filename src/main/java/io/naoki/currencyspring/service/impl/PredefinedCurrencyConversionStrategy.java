package io.naoki.currencyspring.service.impl;

import io.naoki.currencyspring.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PredefinedCurrencyConversionStrategy extends CurrencyConversionStrategy {

    private String commonCurrencyCode = "USD";

    @Autowired
    public PredefinedCurrencyConversionStrategy(ExchangeRateRepository exchangeRateRepository) {
        super(exchangeRateRepository);
    }

    public PredefinedCurrencyConversionStrategy(ExchangeRateRepository exchangeRateRepository, String commonCurrencyCode) {
        super(exchangeRateRepository);
        this.commonCurrencyCode = commonCurrencyCode;
    }

    @Override
    protected String getCommonCurrencyCode() {
        return commonCurrencyCode;
    }

}
