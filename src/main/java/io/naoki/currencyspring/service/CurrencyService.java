package io.naoki.currencyspring.service;

import io.naoki.currencyspring.dto.currency.CreateCurrencyDto;
import io.naoki.currencyspring.dto.currency.CurrencyResponseDto;

import java.util.List;

public interface CurrencyService {

    List<CurrencyResponseDto> getAllCurrencies();

    CurrencyResponseDto getCurrencyByCode(String code);

    CurrencyResponseDto createCurrency(CreateCurrencyDto createCurrencyDto);
}
