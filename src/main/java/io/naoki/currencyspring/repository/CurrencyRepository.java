package io.naoki.currencyspring.repository;

import io.naoki.currencyspring.entity.Currency;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends Repository<Currency, Integer> {

    List<Currency> findAll();

    Optional<Currency> findByCode(String code);

    Currency save(Currency currency);
}
