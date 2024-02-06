package io.naoki.currencyspring.service.impl;

import io.naoki.currencyspring.entity.Currency;
import io.naoki.currencyspring.entity.ExchangeRate;
import io.naoki.currencyspring.repository.ExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionStrategyTest {


    private static final String COMMON = "CCC";
    private static final String BASE = "AAA";
    private static final String TARGET = "BBB";
    @Mock
    ExchangeRateRepository exchangeRateRepository;
    CurrencyConversionStrategy conversionStrategy;

    @BeforeEach
    void setUp() {
        conversionStrategy = new CurrencyConversionStrategy(exchangeRateRepository) {
            @Override
            protected String getCommonCurrencyCode() {
                return COMMON;
            }
        };
    }

    @Test
    void convert_BaseCommonAndTargetCommon_RequestedExchangeRate() {
        BigDecimal rate1 = new BigDecimal("100");
        BigDecimal rate2 = new BigDecimal("20");
        ExchangeRate mockBase = new ExchangeRate(null, base(), common(), rate1);
        ExchangeRate mockTarget = new ExchangeRate(null, target(), common(), rate2);

        when(exchangeRateRepository.findByPairCodesBidirectional(BASE, COMMON)).thenReturn(Optional.of(mockBase));
        when(exchangeRateRepository.findByPairCodesBidirectional(TARGET, COMMON)).thenReturn(Optional.of(mockTarget));

        var actualExchangeRate = conversionStrategy.convert(BASE, TARGET);

        BigDecimal expectedRate = new BigDecimal("5");
        ExchangeRate expected = new ExchangeRate(null, base(), target(), expectedRate);
        assertThat(actualExchangeRate).isNotEmpty().hasValue(expected);
    }

    @Test
    void convert_CommonBaseAndTargetCommon_RequestedExchangeRate() {
        BigDecimal rate1 = new BigDecimal("2");
        BigDecimal rate2 = new BigDecimal("2");
        ExchangeRate mockBase = new ExchangeRate(null, common(), base(), rate1);
        ExchangeRate mockTarget = new ExchangeRate(null, target(), common(), rate2);

        when(exchangeRateRepository.findByPairCodesBidirectional(BASE, COMMON)).thenReturn(Optional.of(mockBase));
        when(exchangeRateRepository.findByPairCodesBidirectional(TARGET, COMMON)).thenReturn(Optional.of(mockTarget));

        var actualExchangeRate = conversionStrategy.convert(BASE, TARGET);

        BigDecimal expectedRate = new BigDecimal("0.25");
        ExchangeRate expected = new ExchangeRate(null, base(), target(), expectedRate);
        assertThat(actualExchangeRate).isNotEmpty().hasValue(expected);
    }

    @Test
    void convert_BaseCommonAndCommonTarget_RequestedExchangeRate() {
        BigDecimal rate1 = new BigDecimal("32");
        BigDecimal rate2 = new BigDecimal("2");
        ExchangeRate mockBase = new ExchangeRate(null, base(), common(), rate1);
        ExchangeRate mockTarget = new ExchangeRate(null, common(), target(), rate2);

        when(exchangeRateRepository.findByPairCodesBidirectional(BASE, COMMON)).thenReturn(Optional.of(mockBase));
        when(exchangeRateRepository.findByPairCodesBidirectional(TARGET, COMMON)).thenReturn(Optional.of(mockTarget));

        var actualExchangeRate = conversionStrategy.convert(BASE, TARGET);

        BigDecimal expectedRate = new BigDecimal("64");
        ExchangeRate expected = new ExchangeRate(null, base(), target(), expectedRate);
        assertThat(actualExchangeRate).isNotEmpty().hasValue(expected);
    }

    @Test
    void convert_CommonBaseAndCommonTarget_RequestedExchangeRate() {
        BigDecimal rate1 = new BigDecimal("20");
        BigDecimal rate2 = new BigDecimal("100");
        ExchangeRate mockBase = new ExchangeRate(null, common(), base(), rate1);
        ExchangeRate mockTarget = new ExchangeRate(null, common(), target(), rate2);

        when(exchangeRateRepository.findByPairCodesBidirectional(BASE, COMMON)).thenReturn(Optional.of(mockBase));
        when(exchangeRateRepository.findByPairCodesBidirectional(TARGET, COMMON)).thenReturn(Optional.of(mockTarget));

        var actualExchangeRate = conversionStrategy.convert(BASE, TARGET);

        BigDecimal expectedRate = new BigDecimal("5");
        ExchangeRate expected = new ExchangeRate(null, base(), target(), expectedRate);
        assertThat(actualExchangeRate).isNotEmpty().hasValue(expected);
    }

    Currency common() {
        return new Currency(null, COMMON, null, null);
    }

    Currency base() {
        return new Currency(null, BASE, null, null);
    }

    Currency target() {
        return new Currency(null, TARGET, null, null);
    }


}