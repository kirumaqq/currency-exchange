package io.naoki.currencyspring.exchangerate;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExchangeRateMapper {

    ExchangeRateResponseDto toResponseDto(ExchangeRate exchangeRate);
}
