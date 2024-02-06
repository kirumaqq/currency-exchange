package io.naoki.currencyspring.service.impl;

import io.naoki.currencyspring.entity.ExchangeRate;
import io.naoki.currencyspring.infrastructure.ExchangeRateUtils;
import io.naoki.currencyspring.repository.ExchangeRateRepository;
import io.naoki.currencyspring.service.ConversionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BidirectionalConversionStrategy implements ConversionStrategy {

    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    public Optional<ExchangeRate> convert(String from, String to) {
        var exchangeRateOpt = exchangeRateRepository.findByPairCodesBidirectional(from, to);

        return exchangeRateOpt
                .map(exchangeRate -> ExchangeRateUtils.rearrangeExchangeRateBy(from, exchangeRate, DIVIDING_CONTEXT));
    }
}
