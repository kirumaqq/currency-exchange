package io.naoki.currencyspring.exchangerate;

import io.naoki.currencyspring.TestCurrencySpringApplication;
import io.naoki.currencyspring.entity.Currency;
import io.naoki.currencyspring.entity.ExchangeRate;
import io.naoki.currencyspring.repository.CurrencyRepository;
import io.naoki.currencyspring.repository.ExchangeRateRepository;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Transactional
@SpringBootTest(classes = TestCurrencySpringApplication.class)
class ExchangeRateRepositoryIT {

    private static final Offset<BigDecimal> delta = within(BigDecimal.valueOf(0.000000001));

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Test
    void findAll_DoesNotThrow_ListSizeIsNotZero() {
        saveExchangeRate();

        List<ExchangeRate> exchangeRateList = exchangeRateRepository.findAll();

        assertThat(exchangeRateList).hasSizeGreaterThan(0);
    }

    @Test
    void findByPairCodes_ValidPair_ReturnsValidExchangeRate() {
        ExchangeRate origin = saveExchangeRate();

        ExchangeRate result = exchangeRateRepository.findByPairCodes(
                origin.baseCurrency().code(), origin.targetCurrency().code()).orElseThrow();

        assertThat(result).isEqualTo(origin);
    }

    @Test
    void findByPairCodes_InvalidPair_ReturnsEmptyOptional() {

        Optional<ExchangeRate> exchangeRateOpt = exchangeRateRepository.findByPairCodes("AAA", "BBB");

        assertThat(exchangeRateOpt).isEmpty();
    }

    @Test
    void save_returnsNotNullId() {
        Currency savedFrom = saveCurrency("AAA");
        Currency savedTo = saveCurrency("BBB");

        Integer id = assertDoesNotThrow(() ->
                exchangeRateRepository.save(savedFrom.id(), savedTo.id(), BigDecimal.ONE));

        assertThat(id).isNotNull();
    }

    @Test
    void updateByPairCodes_ValidPair_ReturnsUpdatedExchangeRate() {
        ExchangeRate origin = saveExchangeRate();

        ExchangeRate updated = exchangeRateRepository
                .updateByPairCodes(origin.baseCurrency().code(), origin.targetCurrency().code(), BigDecimal.TEN)
                .orElseThrow();

        ExchangeRate result = exchangeRateRepository.findByPairCodes(origin.baseCurrency().code(),
                origin.targetCurrency().code()).orElseThrow();
        assertThat(updated).isEqualTo(result);
        assertThat(updated.rate()).isCloseTo(BigDecimal.TEN, delta);
    }

    @Test
    void updateByPairCodes_InvalidPair_ReturnsEmptyOptional() {
        Optional<ExchangeRate> exchangeRateOpt = exchangeRateRepository.updateByPairCodes("AAA", "BBB", BigDecimal.ONE);

        assertThat(exchangeRateOpt).isEmpty();
    }

    Currency saveCurrency(String currencyCode) {
        return currencyRepository.save(new Currency(null, currencyCode, "name", "s"));
    }

    ExchangeRate saveExchangeRate() {
        Currency base = saveCurrency("AAA");
        Currency target = saveCurrency("BBB");
        int id = exchangeRateRepository.save(base.id(), target.id(), BigDecimal.ONE);
        return new ExchangeRate(id, base, target, BigDecimal.ONE.setScale(6, RoundingMode.CEILING));
    }
}