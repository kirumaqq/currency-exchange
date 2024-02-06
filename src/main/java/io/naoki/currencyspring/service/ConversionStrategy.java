package io.naoki.currencyspring.service;

import io.naoki.currencyspring.entity.ExchangeRate;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;

public interface ConversionStrategy {

    MathContext DIVIDING_CONTEXT = new MathContext(6, RoundingMode.HALF_EVEN);


    Optional<ExchangeRate> convert(String from, String to);
}
