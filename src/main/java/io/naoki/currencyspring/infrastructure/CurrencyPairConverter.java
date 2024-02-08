package io.naoki.currencyspring.infrastructure;

import io.naoki.currencyspring.dto.currency.CurrencyPair;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class CurrencyPairConverter implements Converter<String, CurrencyPair> {

    private final Validator validator;
    @Override
    public CurrencyPair convert(String source) {
        CurrencyPair currencyPair = fetchCurrencyPair(source);
        validateCurrencyPair(currencyPair);
        return currencyPair;
    }

    private CurrencyPair fetchCurrencyPair(String source) {
        if (source == null || source.length() != 6) {
            return new CurrencyPair("", "");
        }
        String base = source.substring(0, 3);
        String target = source.substring(3);
        return new CurrencyPair(base, target);
    }

    private void validateCurrencyPair(CurrencyPair currencyPair) {
        Errors errors = validator.validateObject(currencyPair);

        errors.failOnError(s -> {
            String detail = getMessage(errors);
            return new IllegalArgumentException(detail);
        });
    }

    private String getMessage(Errors errors) {
        return errors.getAllErrors().get(0).getDefaultMessage();
    }
}
