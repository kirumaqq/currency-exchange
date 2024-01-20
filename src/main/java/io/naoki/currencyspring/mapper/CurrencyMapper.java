package io.naoki.currencyspring.mapper;

import io.naoki.currencyspring.dto.currency.CreateCurrencyDto;
import io.naoki.currencyspring.dto.currency.CurrencyResponseDto;
import io.naoki.currencyspring.entity.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CurrencyMapper {

    @Mapping(target = "name", source = "fullName")
    CurrencyResponseDto toResponseDto(Currency currency);

    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "id", ignore = true)
    Currency fromCreateDtoToCurrency(CreateCurrencyDto currencyDto);
}
