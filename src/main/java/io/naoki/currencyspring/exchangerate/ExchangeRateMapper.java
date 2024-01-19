package io.naoki.currencyspring.exchangerate;

import org.mapstruct.Mapper;

@Mapper
public interface ExchangeRateMapper {

    ExchangeRateResponseDto toResponseDto(ExchangeRate exchangeRate);
}
