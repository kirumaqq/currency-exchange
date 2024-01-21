package io.naoki.currencyspring.infrastructure;

import io.naoki.currencyspring.dto.currency.CurrencyPair;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyPairConverter implements Converter<String, CurrencyPair> {
    @Override
    public CurrencyPair convert(String source) {
        if (source.length() != 6) throw new IllegalArgumentException("Both currencies codes must be equal to 3");
        String base = source.substring(0, 3);
        String target = source.substring(3);
        return new CurrencyPair(base.toUpperCase(), target.toUpperCase());
    }
}
