package io.naoki.currencyspring.service.impl;

import io.naoki.currencyspring.dto.currency.CreateCurrencyDto;
import io.naoki.currencyspring.dto.currency.CurrencyResponseDto;
import io.naoki.currencyspring.entity.Currency;
import io.naoki.currencyspring.exceptions.ResourceAlreadyExistException;
import io.naoki.currencyspring.exceptions.ResourceNotFoundException;
import io.naoki.currencyspring.mapper.CurrencyMapper;
import io.naoki.currencyspring.repository.CurrencyRepository;
import io.naoki.currencyspring.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Currency with this code wasn't found")));
    }

    @Override
    public CurrencyResponseDto createCurrency(CreateCurrencyDto createCurrencyDto) {
        Currency currency = currencyMapper.fromCreateDtoToCurrency(createCurrencyDto);
        try {
            Currency savedCurrency = currencyRepository.save(currency);
            return currencyMapper.toResponseDto(savedCurrency);
        } catch (DbActionExecutionException e) {
            throw new ResourceAlreadyExistException("Currency with this code already exist");
        }
    }
}
