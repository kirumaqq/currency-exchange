package io.naoki.currencyspring.service.impl;

import io.naoki.currencyspring.entity.Currency;
import io.naoki.currencyspring.entity.ExchangeRate;
import io.naoki.currencyspring.repository.ExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BidirectionalConversionStrategyTest {

    @Mock
    ExchangeRateRepository exchangeRateRepository;

    @InjectMocks
    BidirectionalConversionStrategy bidirectionalConversionStrategy;

    @Test
    void convert_ExchangeRate_ReturnsRequestedExchangeRate() {
        Currency baseCurrency = new Currency(null, "AAA", "name", "A");
        Currency targetCurrency = new Currency(null, "BBB", "name", "B");
        ExchangeRate mockExchangeRate = new ExchangeRate(null, baseCurrency, targetCurrency, BigDecimal.TEN);

        when(exchangeRateRepository.findByPairCodesBidirectional(baseCurrency.code(), targetCurrency.code()))
                .thenReturn(Optional.of(mockExchangeRate));

        var exchangeRateOpt = bidirectionalConversionStrategy
                .convert(baseCurrency.code(), targetCurrency.code());

        ExchangeRate expected = mockExchangeRate;
        assertThat(exchangeRateOpt).isNotEmpty().contains(expected);
    }

    @Test
    void convert_ReversedExchangeRate_ReturnsRequestedExchangeRate() {
        Currency baseCurrency = new Currency(null, "AAA", "name", "A");
        Currency targetCurrency = new Currency(null, "BBB", "name", "B");
        ExchangeRate mockExchangeRate = new ExchangeRate(null, baseCurrency, targetCurrency, BigDecimal.TEN);

        when(exchangeRateRepository.findByPairCodesBidirectional(targetCurrency.code(), baseCurrency.code()))
                .thenReturn(Optional.of(mockExchangeRate));

        var exchangeRateOpt = bidirectionalConversionStrategy
                .convert(targetCurrency.code(), baseCurrency.code());

        ExchangeRate expected = new ExchangeRate(null, targetCurrency, baseCurrency,
                BigDecimal.valueOf(0.1));
        assertThat(exchangeRateOpt).isNotEmpty().contains(expected);
    }

    @Test
    void convert_NotFoundExchangeRate_ReturnsEmptyOptional() {
        when(exchangeRateRepository.findByPairCodesBidirectional(anyString(), anyString()))
                .thenReturn(Optional.empty());

        var exchangeRateOpt = bidirectionalConversionStrategy.convert("AAA", "BBB");

        assertThat(exchangeRateOpt).isEmpty();
    }


}