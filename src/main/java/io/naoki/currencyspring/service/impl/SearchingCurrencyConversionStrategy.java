package io.naoki.currencyspring.service.impl;

import io.naoki.currencyspring.repository.ExchangeRateRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Order(3)
@Service
public class SearchingCurrencyConversionStrategy extends CurrencyConversionStrategy {

    protected SearchingCurrencyConversionStrategy(ExchangeRateRepository exchangeRateRepository) {
        super(exchangeRateRepository);
    }

    @Override
    protected String getCommonCurrencyCode(String code1, String code2) {

        List<String> allPairCodes1 = getAllPairCodes(code1);
        List<String> allPairCodes2 = getAllPairCodes(code2);

        return findMatch(allPairCodes1, allPairCodes2);
    }

    private List<String> getAllPairCodes(String code1) {
        List<String> targetPairCodes = exchangeRateRepository.findAllPairsCodesByBaseCode(code1);
        List<String> basePairCodes = exchangeRateRepository.findAllPairsCodesByTargetCode(code1);
        List<String> res = new ArrayList<>(targetPairCodes);
        res.addAll(basePairCodes);
        return res;
    }

    private String findMatch(List<String> list1, List<String> list2) {
        for (String s1 : list1) {
            for (String s2 : list2) {
                if (s1.equals(s2)) {
                    return s1;
                }
            }
        }
        return null;
    }


}
