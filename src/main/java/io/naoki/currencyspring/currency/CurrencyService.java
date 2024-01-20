package io.naoki.currencyspring.currency;

import java.util.List;

public interface CurrencyService {

    List<CurrencyResponseDto> getAllCurrencies();

    CurrencyResponseDto getCurrencyByCode(String code);

    CurrencyResponseDto createCurrency(CreateCurrencyDto createCurrencyDto);
}
