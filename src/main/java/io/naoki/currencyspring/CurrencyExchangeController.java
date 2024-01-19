package io.naoki.currencyspring;

import io.naoki.currencyspring.currency.CreateCurrencyDto;
import io.naoki.currencyspring.currency.CurrencyResponseDto;
import io.naoki.currencyspring.currency.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
@RestController
public class CurrencyExchangeController {

    private final CurrencyService currencyService;

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
                        .path("/currency/{code}")
                        .build(createCurrencyDto.code()))
                .body(currencyService.createCurrency(createCurrencyDto));
    }

}
