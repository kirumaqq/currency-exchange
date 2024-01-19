package io.naoki.currencyspring.currency.impl;

import io.naoki.currencyspring.currency.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;


    private final CurrencyMapper currencyMapper;

    @Override
    public List<CurrencyResponseDto> getAllCurrencies() {
        return currencyRepository.findAll().stream()
                .map(currencyMapper::toResponseDto)
                .toList();
    }

    @Override
    public CurrencyResponseDto getCurrencyByCode(String code) {
        return currencyMapper.toResponseDto(currencyRepository.findByCode(code)
                .orElseThrow(CurrencyNotFoundException::new));
    }

    @Override
    public CurrencyResponseDto createCurrency(CreateCurrencyDto createCurrencyDto) {
        Currency currency = currencyMapper.fromCreateDtoToCurrency(createCurrencyDto);
        return currencyMapper.toResponseDto(currencyRepository.save(currency));
    }
}
