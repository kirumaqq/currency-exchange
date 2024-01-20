package io.naoki.currencyspring;

import io.naoki.currencyspring.currency.CreateCurrencyDto;
import io.naoki.currencyspring.currency.CurrencyPair;
import io.naoki.currencyspring.currency.CurrencyResponseDto;
import io.naoki.currencyspring.currency.CurrencyService;
import io.naoki.currencyspring.exchangerate.CreateExchangeRateDto;
import io.naoki.currencyspring.exchangerate.ExchangeRateResponseDto;
import io.naoki.currencyspring.exchangerate.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
@RestController
public class CurrencyExchangeController {

    private final CurrencyService currencyService;

    private final ExchangeRateService exchangeRateService;

    @GetMapping("/is-alive")
    public String isAlive() {
        return "Server is alive";
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<CurrencyResponseDto>> getAllCurrencies() {
        return ResponseEntity.ok(currencyService.getAllCurrencies());
    }

    @GetMapping("/currency/{code}")
    public ResponseEntity<CurrencyResponseDto> getCurrencyByCode(@PathVariable String code) {
        return ResponseEntity.ok(currencyService.getCurrencyByCode(code));
    }


    @PostMapping(path = "/currencies", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<CurrencyResponseDto> createCurrency(CreateCurrencyDto createCurrencyDto,
                                                              UriComponentsBuilder uriBuilder) {
        return ResponseEntity.created(uriBuilder
                        .path("api/v1/currency/{code}")
                        .build(createCurrencyDto.code()))
                .body(currencyService.createCurrency(createCurrencyDto));
    }

    @GetMapping("/exchangeRates")
    public ResponseEntity<List<ExchangeRateResponseDto>> getAllExchangeRates() {
        return ResponseEntity.ok(exchangeRateService.getAllExchangeRates());
    }


    @GetMapping("/exchangeRate/{pair}")
    public ResponseEntity<ExchangeRateResponseDto> getExchangeRateByCodes(@PathVariable CurrencyPair pair) {
        return ResponseEntity.ok(exchangeRateService.getExchangeRateByCodes(pair.baseCurrencyCode(),
                pair.targetCurrencyCode()));
    }

    @PostMapping(path = "/exchangeRates", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<ExchangeRateResponseDto> createExchangeRate(CreateExchangeRateDto createExchangeRateDto,
                                                                      UriComponentsBuilder uriBuilder) {
        return ResponseEntity.created(uriBuilder
                        .path("api/v1/exchangeRate/{codes}")
                        .build(createExchangeRateDto.baseCurrencyCode()
                                .concat(createExchangeRateDto.targetCurrencyCode())))
                .body(exchangeRateService.createExchangeRate(createExchangeRateDto));
    }

    @PatchMapping(path = "/exchangeRate/{pair}")
    public ResponseEntity<ExchangeRateResponseDto> editExchangeRate(@PathVariable CurrencyPair pair,
                                                                    BigDecimal rate) {
        return ResponseEntity.ok(exchangeRateService.updateExchangeRate(pair, rate));
    }

}
