package io.naoki.currencyspring.service;

import io.naoki.currencyspring.dto.conversion.ConversionRequest;
import io.naoki.currencyspring.dto.conversion.ConversionResponse;

public interface ConversionService {

    ConversionResponse convertCurrencyAmount(ConversionRequest conversionRequest);
}
