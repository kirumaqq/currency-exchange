package io.naoki.currencyspring.currency;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyPairConverter implements Converter<String, CurrencyPair> {
    @Override
    public CurrencyPair convert(String source) {
        String base = source.substring(0, 3);
        String target = source.substring(3);
        return new CurrencyPair(base.toUpperCase(), target.toUpperCase());
    }
}
