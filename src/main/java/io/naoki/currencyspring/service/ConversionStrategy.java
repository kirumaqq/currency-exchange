package io.naoki.currencyspring.service;

import io.naoki.currencyspring.entity.ExchangeRate;

import java.util.Optional;

public interface ConversionStrategy {

    Optional<ExchangeRate> convert(String from, String to);
}
