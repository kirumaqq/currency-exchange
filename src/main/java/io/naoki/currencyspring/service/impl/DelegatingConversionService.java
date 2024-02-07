package io.naoki.currencyspring.service.impl;

import io.naoki.currencyspring.dto.conversion.ConversionRequest;
import io.naoki.currencyspring.dto.conversion.ConversionResponse;
import io.naoki.currencyspring.entity.ExchangeRate;
import io.naoki.currencyspring.exceptions.ResourceNotFoundException;
import io.naoki.currencyspring.service.ConversionService;
import io.naoki.currencyspring.service.ConversionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DelegatingConversionService implements ConversionService {

    private final List<ConversionStrategy> conversionStrategyList;

    @Override
    public ConversionResponse convertCurrencyAmount(ConversionRequest conversionReq) {

        for (ConversionStrategy conversionStrategy : conversionStrategyList) {
            var exchangeRateOpt = conversionStrategy.convert(conversionReq.from(), conversionReq.to());
            if (exchangeRateOpt.isPresent()) {
                return buildConversionResponse(conversionReq, exchangeRateOpt.get());
            }
        }

        throw new ResourceNotFoundException("Could not convert");
    }

    private ConversionResponse buildConversionResponse(ConversionRequest conversionRequest, ExchangeRate exchangeRate) {
        return new ConversionResponse(
                exchangeRate.baseCurrency(),
                exchangeRate.targetCurrency(),
                exchangeRate.rate(),
                conversionRequest.amount(),
                calculateConvertedAmount(exchangeRate.rate(), conversionRequest.amount())
        );
    }

    private BigDecimal calculateConvertedAmount(BigDecimal rate, BigDecimal amount) {
        return rate.multiply(amount);
    }
}
