package io.naoki.currencyspring.service;

import io.naoki.currencyspring.dto.currency.CurrencyPair;
import io.naoki.currencyspring.dto.exchangerate.CreateExchangeRateDto;
import io.naoki.currencyspring.dto.exchangerate.ExchangeRateResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateService {

    List<ExchangeRateResponseDto> getAllExchangeRates();

    ExchangeRateResponseDto getExchangeRateByCodes(String baseCode, String targetCode);

    ExchangeRateResponseDto createExchangeRate(CreateExchangeRateDto createExchangeRateDto);

    ExchangeRateResponseDto updateExchangeRate(CurrencyPair pair, BigDecimal rate);

}
