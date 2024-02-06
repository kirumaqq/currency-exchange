package io.naoki.currencyspring.service.impl;

import io.naoki.currencyspring.repository.ExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchingCurrencyConversionStrategyTest {

    private static final String BASE = "AAA";
    private static final String TARGET = "BBB";

    @Mock
    ExchangeRateRepository exchangeRateRepository;

    @InjectMocks
    SearchingCurrencyConversionStrategy conversionStrategy;

    @Test
    void getCommonCurrencyCode_OneMatch_ReturnsMatch() {
        String match = "XXX";
        List<String> mockList1 = List.of("QQQ", "WWW", "EEE", "RRR");
        List<String> mockList2 = List.of("TTT", "YYY", match);
        List<String> mockList3 = List.of("ZZZ", "CCC", "VVV", match);
        List<String> mockList4 = List.of("PPP", "LLL", "MMM");

        when(exchangeRateRepository.findAllPairsCodesByBaseCode(BASE)).thenReturn(mockList1);
        when(exchangeRateRepository.findAllPairsCodesByTargetCode(BASE)).thenReturn(mockList2);
        when(exchangeRateRepository.findAllPairsCodesByBaseCode(TARGET)).thenReturn(mockList3);
        when(exchangeRateRepository.findAllPairsCodesByTargetCode(TARGET)).thenReturn(mockList4);

        String actual = conversionStrategy.getCommonCurrencyCode(BASE, TARGET);

        assertThat(actual).isEqualTo(match);
    }

    @Test
    void getCommonCurrencyCode_NoMatches_ReturnsNull() {
        List<String> mockList1 = List.of("PPP");
        List<String> mockList2 = List.of("LLL");
        List<String> mockList3 = List.of("NNN");
        List<String> mockList4 = List.of("MMM");

        when(exchangeRateRepository.findAllPairsCodesByBaseCode(BASE)).thenReturn(mockList1);
        when(exchangeRateRepository.findAllPairsCodesByTargetCode(BASE)).thenReturn(mockList2);
        when(exchangeRateRepository.findAllPairsCodesByBaseCode(TARGET)).thenReturn(mockList3);
        when(exchangeRateRepository.findAllPairsCodesByTargetCode(TARGET)).thenReturn(mockList4);

        String actual = conversionStrategy.getCommonCurrencyCode(BASE, TARGET);

        assertThat(actual).isNull();
    }

    @Test
    void getCommonCurrencyCode_NoMatchesAndDirectRelation_ReturnsNull() {
        List<String> mockList1 = List.of(TARGET);
        List<String> mockList2 = List.of("NNN");
        List<String> mockList3 = List.of(BASE);
        List<String> mockList4 = List.of("MMM");

        when(exchangeRateRepository.findAllPairsCodesByBaseCode(BASE)).thenReturn(mockList1);
        when(exchangeRateRepository.findAllPairsCodesByTargetCode(BASE)).thenReturn(mockList2);
        when(exchangeRateRepository.findAllPairsCodesByBaseCode(TARGET)).thenReturn(mockList3);
        when(exchangeRateRepository.findAllPairsCodesByTargetCode(TARGET)).thenReturn(mockList4);

        String actual = conversionStrategy.getCommonCurrencyCode(BASE, TARGET);

        assertThat(actual).isNull();
    }
}