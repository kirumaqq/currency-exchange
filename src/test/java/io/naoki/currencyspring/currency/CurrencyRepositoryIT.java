package io.naoki.currencyspring.currency;

import io.naoki.currencyspring.TestCurrencySpringApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Transactional
@SpringBootTest(classes = TestCurrencySpringApplication.class)
class CurrencyRepositoryIT {

    @Autowired
    CurrencyRepository currencyRepository;

    @Test
    void findAll_DoesNotThrow_ListSizeIsNotZero() {
        Currency currency = new Currency(null, "TST", "Test", "T");
        currencyRepository.save(currency);
        List<Currency> currencyList = assertDoesNotThrow(currencyRepository::findAll);
        assertThat(currencyList).hasSizeGreaterThan(0);
    }

    @Test
    void findByCode_ValidCode_DoesNotThrow() {
        Currency currency = new Currency(null, "TST", "Test", "T");
        currencyRepository.save(currency);
        Currency savedCurrency = currencyRepository.findByCode(currency.code()).orElseThrow();
        assertThat(savedCurrency.fullName()).isEqualTo(currency.fullName());
    }


    @Test
    void save_ReturnsEntityWithId() {
        Currency currency = new Currency(null, "TST", "Test", "T");
        Currency savedCurrency = currencyRepository.save(currency);
        assertThat(savedCurrency.id()).isNotNull();
    }
}