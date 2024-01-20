package io.naoki.currencyspring.exchangerate;

import io.naoki.currencyspring.currency.CurrencyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CurrencyMapper.class)
public interface ExchangeRateMapper {

    ExchangeRateResponseDto toResponseDto(ExchangeRate exchangeRate);
}
