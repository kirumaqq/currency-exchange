package io.naoki.currencyspring.exchangerate;

import io.naoki.currencyspring.currency.CurrencyPair;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateService {

    List<ExchangeRateResponseDto> getAllExchangeRates();

    ExchangeRateResponseDto getExchangeRateByCodes(String baseCode, String targetCode);

    ExchangeRateResponseDto createExchangeRate(CreateExchangeRateDto createExchangeRateDto);

    ExchangeRateResponseDto updateExchangeRate(CurrencyPair pair, BigDecimal rate);

}
