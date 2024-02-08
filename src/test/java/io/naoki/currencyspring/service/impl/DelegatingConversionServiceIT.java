package io.naoki.currencyspring.service.impl;

import io.naoki.currencyspring.TestCurrencySpringApplication;
import io.naoki.currencyspring.dto.conversion.ConversionRequest;
import io.naoki.currencyspring.dto.conversion.ConversionResponse;
import io.naoki.currencyspring.entity.Currency;
import io.naoki.currencyspring.repository.CurrencyRepository;
import io.naoki.currencyspring.repository.ExchangeRateRepository;
import io.naoki.currencyspring.service.ConversionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest(classes = TestCurrencySpringApplication.class)
class DelegatingConversionServiceIT {

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    @Autowired
    ConversionService conversionService;


    @Test
    void convertCurrencyAmount_DirectMatch_ReturnsConversionResponse() {
        Currency base = new Currency(null, "AAA", "name1", "A");
        Currency target = new Currency(null, "BBB", "name2", "B");
        Integer baseId = currencyRepository.save(base).id();
        Integer targetId = currencyRepository.save(target).id();

        BigDecimal rate = new BigDecimal("12.123456");
        BigDecimal amount = new BigDecimal("11.2");

        exchangeRateRepository.save(baseId, targetId, rate);
        ConversionRequest conversionRequest = new ConversionRequest(base.code(), target.code(), amount);

        BigDecimal expectedRate = rate;
        BigDecimal expectedConvertedAmount = new BigDecimal("135.7827072");

        ConversionResponse conversionResponse = conversionService.convertCurrencyAmount(conversionRequest);

        assertAll(
                () -> assertThat(conversionResponse.baseCurrency().code()).isEqualTo(base.code()),
                () -> assertThat(conversionResponse.targetCurrency().code()).isEqualTo(target.code()),
                () -> assertThat(conversionResponse.amount()).isEqualTo(amount),
                () -> assertThat(conversionResponse.rate()).isEqualTo(expectedRate),
                () -> assertThat(conversionResponse.convertedAmount()).isEqualTo(expectedConvertedAmount)
        );
    }

    @Test
    void convertCurrencyAmount_ReverseMatch_ReturnsConversionResponse() {
        Currency base = new Currency(null, "AAA", "name1", "A");
        Currency target = new Currency(null, "BBB", "name2", "B");
        Integer baseId = currencyRepository.save(base).id();
        Integer targetId = currencyRepository.save(target).id();

        BigDecimal rate = new BigDecimal("2.34");
        BigDecimal amount = new BigDecimal("11.2");

        exchangeRateRepository.save(targetId, baseId, rate);

        ConversionRequest conversionRequest = new ConversionRequest(base.code(), target.code(), amount);
        ConversionResponse conversionResponse = conversionService.convertCurrencyAmount(conversionRequest);

        BigDecimal expectedRate = new BigDecimal("0.427350");
        BigDecimal expectedConvertedAmount = new BigDecimal("4.7863200");

        assertAll(
                () -> assertThat(conversionResponse.baseCurrency().code()).isEqualTo(base.code()),
                () -> assertThat(conversionResponse.targetCurrency().code()).isEqualTo(target.code()),
                () -> assertThat(conversionResponse.amount()).isEqualTo(amount),
                () -> assertThat(conversionResponse.rate()).isEqualTo(expectedRate),
                () -> assertThat(conversionResponse.convertedAmount()).isEqualTo(expectedConvertedAmount)
        );
    }

    @Test
    void convertCurrencyAmount_CommonCurrencyMatch_ReturnsConversionResponse() {
        Currency base = new Currency(null, "AAA", "name1", "A");
        Currency target = new Currency(null, "BBB", "name2", "B");
        Currency commonCurrency = new Currency(null, "CCC", "name3", "C");
        Integer baseId = currencyRepository.save(base).id();
        Integer targetId = currencyRepository.save(target).id();
        Integer commonId = currencyRepository.save(commonCurrency).id();

        BigDecimal commonToBaseRate = new BigDecimal("98.235224");
        BigDecimal targetToCommonRate = new BigDecimal("0.002323"); //common to target = 430.477830
        BigDecimal amount = new BigDecimal("1000242.500");

        exchangeRateRepository.save(commonId, baseId, commonToBaseRate);
        exchangeRateRepository.save(targetId, commonId, targetToCommonRate);


        ConversionRequest conversionRequest = new ConversionRequest(base.code(), target.code(), amount);
        ConversionResponse conversionResponse = conversionService.convertCurrencyAmount(conversionRequest);

        BigDecimal expectedRate = new BigDecimal("4.38211");
        BigDecimal expectedConvertedAmount = new BigDecimal("4383172.66167500");

        assertAll(
                () -> assertThat(conversionResponse.baseCurrency().code()).isEqualTo(base.code()),
                () -> assertThat(conversionResponse.targetCurrency().code()).isEqualTo(target.code()),
                () -> assertThat(conversionResponse.amount()).isEqualTo(amount),
                () -> assertThat(conversionResponse.rate()).isEqualTo(expectedRate),
                () -> assertThat(conversionResponse.convertedAmount()).isEqualTo(expectedConvertedAmount)
        );
    }


}