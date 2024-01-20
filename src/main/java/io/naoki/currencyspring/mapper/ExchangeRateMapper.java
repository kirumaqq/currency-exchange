package io.naoki.currencyspring.mapper;

import io.naoki.currencyspring.dto.exchangerate.ExchangeRateResponseDto;
import io.naoki.currencyspring.entity.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CurrencyMapper.class)
public interface ExchangeRateMapper {

    ExchangeRateResponseDto toResponseDto(ExchangeRate exchangeRate);
}
