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
import static org.junit.jupiter.api.Assertions.assertAll;
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

    @Test
    void findByPairCodesBidirectional_ReversedCodePair_ReturnsSameExchangeRate() {
        saveExchangeRate();

        var exchangeRateOpt1 = exchangeRateRepository.findByPairCodesBidirectional("AAA", "BBB");
        var exchangeRateOpt2 = exchangeRateRepository.findByPairCodesBidirectional("BBB", "AAA");

        assertAll(() -> {
            assertThat(exchangeRateOpt1).isNotEmpty();
            assertThat(exchangeRateOpt2).isNotEmpty();
        });
        assertThat(exchangeRateOpt1.get()).isEqualTo(exchangeRateOpt2.get());
    }


    @Test
    void findAllPairsCodesByBaseCode_OneValidPair_ReturnsSingletonList() {
        Currency main = new Currency(null, "AAA", "name1", "A");
        Currency targetPair = new Currency(null, "BBB", "name2", "B");
        Currency basePair = new Currency(null, "CCC", "name3", "C");
        Integer mainId = currencyRepository.save(main).id();
        Integer targetId = currencyRepository.save(targetPair).id();
        Integer baseId = currencyRepository.save(basePair).id();

        exchangeRateRepository.save(mainId, targetId, BigDecimal.ONE);
        exchangeRateRepository.save(baseId, mainId, BigDecimal.ONE);

        var targetPairs = exchangeRateRepository.findAllPairsCodesByBaseCode(main.code());

        assertAll(() -> {
            assertThat(targetPairs).hasSize(1);
            assertThat(targetPairs.get(0)).isEqualTo(targetPair.code());
        });
    }

    @Test
    void findAllPairsCodesByTargetCode_OneValidPair_ReturnsSingletonList() {
        Currency main = new Currency(null, "AAA", "name1", "A");
        Currency targetPair = new Currency(null, "BBB", "name2", "B");
        Currency basePair = new Currency(null, "CCC", "name3", "C");
        Integer mainId = currencyRepository.save(main).id();
        Integer targetId = currencyRepository.save(targetPair).id();
        Integer baseId = currencyRepository.save(basePair).id();

        exchangeRateRepository.save(mainId, targetId, BigDecimal.ONE);
        exchangeRateRepository.save(baseId, mainId, BigDecimal.ONE);

        var basePairs = exchangeRateRepository.findAllPairsCodesByTargetCode(main.code());

        assertAll(() -> {
            assertThat(basePairs).hasSize(1);
            assertThat(basePairs.get(0)).isEqualTo(basePair.code());
        });
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