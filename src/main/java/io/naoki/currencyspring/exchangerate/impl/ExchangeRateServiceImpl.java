package io.naoki.currencyspring.exchangerate.impl;

import io.naoki.currencyspring.currency.CurrencyPair;
import io.naoki.currencyspring.currency.CurrencyResponseDto;
import io.naoki.currencyspring.currency.CurrencyService;
import io.naoki.currencyspring.exchangerate.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

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
                .orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND));
        return exchangeRateMapper.toResponseDto(exchangeRate);
    }

    @Override
    public ExchangeRateResponseDto createExchangeRate(CreateExchangeRateDto createExchangeRateDto) {
        CurrencyResponseDto base = currencyService.getCurrencyByCode(createExchangeRateDto.baseCurrencyCode());
        CurrencyResponseDto target = currencyService.getCurrencyByCode(createExchangeRateDto.targetCurrencyCode());
        BigDecimal rate = createExchangeRateDto.rate();

        Integer id = exchangeRateRepository.save(base.id(), target.id(), rate);
        return new ExchangeRateResponseDto(id, base, target, rate);
    }

    @Override
    public ExchangeRateResponseDto updateExchangeRate(CurrencyPair pair, BigDecimal rate) {
        ExchangeRate updatedExchangeRate = exchangeRateRepository.updateByPairCodes(pair.baseCurrencyCode(),
                pair.targetCurrencyCode(), rate).orElseThrow(ExchangeRateNotFoundException::new);
        return exchangeRateMapper.toResponseDto(updatedExchangeRate);
    }


}
