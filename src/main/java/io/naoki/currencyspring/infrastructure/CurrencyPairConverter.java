package io.naoki.currencyspring.infrastructure;

import io.naoki.currencyspring.dto.currency.CurrencyPair;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyPairConverter implements Converter<String, CurrencyPair> {

    private static final String REGEX = "\\A\\pL+\\z";

    @Override
    public CurrencyPair convert(String source) {
        validatePair(source);
        String base = source.substring(0, 3);
        String target = source.substring(3);
        return new CurrencyPair(base.toUpperCase(), target.toUpperCase());
    }

    private static void validatePair(String source) {
        if (source == null) throw new IllegalArgumentException("Currency codes must not be empty");
        if (source.length() != 6) throw new IllegalArgumentException("Both currencies codes must be equal to 3");
        if (!source.matches(REGEX))
            throw new IllegalArgumentException("Currencies must have alphabetic characters only");
    }
}
