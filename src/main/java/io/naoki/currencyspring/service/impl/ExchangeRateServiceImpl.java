package io.naoki.currencyspring.service.impl;

import io.naoki.currencyspring.dto.currency.CurrencyPair;
import io.naoki.currencyspring.dto.currency.CurrencyResponseDto;
import io.naoki.currencyspring.dto.exchangerate.CreateExchangeRateDto;
import io.naoki.currencyspring.dto.exchangerate.ExchangeRateResponseDto;
import io.naoki.currencyspring.entity.ExchangeRate;
import io.naoki.currencyspring.exceptions.ResourceAlreadyExistException;
import io.naoki.currencyspring.exceptions.ResourceNotFoundException;
import io.naoki.currencyspring.mapper.ExchangeRateMapper;
import io.naoki.currencyspring.repository.ExchangeRateRepository;
import io.naoki.currencyspring.service.CurrencyService;
import io.naoki.currencyspring.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    private final ExchangeRateMapper exchangeRateMapper;

    private final CurrencyService currencyService;

    @Override
    public List<ExchangeRateResponseDto> getAllExchangeRates() {
        return exchangeRateRepository.findAll().stream()
                .map(exchangeRateMapper::toResponseDto)
                .toList();
    }

    @Override
    public ExchangeRateResponseDto getExchangeRateByCodes(String baseCode, String targetCode) {
        ExchangeRate exchangeRate = exchangeRateRepository.findByPairCodes(baseCode, targetCode)
                .orElseThrow(() -> new ResourceNotFoundException("Exchange rate for this pair wasn't found"));
        return exchangeRateMapper.toResponseDto(exchangeRate);
    }

    @Override
    public ExchangeRateResponseDto createExchangeRate(CreateExchangeRateDto createExchangeRateDto) {
        CurrencyResponseDto base = currencyService.getCurrencyByCode(createExchangeRateDto.baseCurrencyCode());
        CurrencyResponseDto target = currencyService.getCurrencyByCode(createExchangeRateDto.targetCurrencyCode());
        BigDecimal rate = createExchangeRateDto.rate();

        try {
            Integer id = exchangeRateRepository.save(base.id(), target.id(), rate);
            return new ExchangeRateResponseDto(id, base, target, rate);
        } catch (DuplicateKeyException e) {
            throw new ResourceAlreadyExistException("Exchange rate for this currency pair already exist");
        }
    }

    @Override
    public ExchangeRateResponseDto updateExchangeRate(CurrencyPair pair, BigDecimal rate) {
        ExchangeRate updatedExchangeRate = exchangeRateRepository
                .updateByPairCodes(pair.baseCurrencyCode(), pair.targetCurrencyCode(), rate)
                .orElseThrow(() -> new ResourceNotFoundException("Exchange rate for this pair wasn't found"));
        return exchangeRateMapper.toResponseDto(updatedExchangeRate);
    }


}
