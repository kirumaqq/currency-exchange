package io.naoki.currencyspring.repository;

import io.naoki.currencyspring.entity.ExchangeRate;
import io.naoki.currencyspring.mapper.ExchangeRateRowMapper;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ExchangeRateRepository extends Repository<ExchangeRate, Integer> {

    @Query(value = """
            SELECT er.id as id, er.rate as rate,
                   er.id as id, er.rate as rate,
                   base.id as base_id, base.code as base_code,
                   base.full_name as base_name, base.sign as base_sign,
                   target.id as target_id, target.code as target_code,
                   target.full_name as target_name, target.sign as target_sign
            FROM exchange_rates er
            JOIN public.currencies base on base.id = er.base_currency_id
            JOIN public.currencies target on target.id = er.target_currency_id
            """, rowMapperClass = ExchangeRateRowMapper.class)
    List<ExchangeRate> findAll();

    @Query(value = """
            SELECT er.id as id, er.rate as rate,
                   er.id as id, er.rate as rate,
                   base.id as base_id, base.code as base_code,
                   base.full_name as base_name, base.sign as base_sign,
                   target.id as target_id, target.code as target_code,
                   target.full_name as target_name, target.sign as target_sign
            FROM exchange_rates er
            JOIN public.currencies base on base.id = er.base_currency_id
            JOIN public.currencies target on target.id = er.target_currency_id
            WHERE
                base.code = :baseCode AND
                target.code = :targetCode
            """, rowMapperClass = ExchangeRateRowMapper.class)
    Optional<ExchangeRate> findByPairCodes(String baseCode, String targetCode);

    @Query("""
            INSERT INTO exchange_rates (base_currency_id, target_currency_id, rate)
            VALUES (:fromId, :toId, :rate)
            RETURNING id
            """)
    Integer save(Integer fromId, Integer toId, BigDecimal rate);

    @Query(value = """
            UPDATE exchange_rates er
            SET rate = :rate
            FROM currencies base, currencies target
            WHERE
                base.code = :baseCode AND target.code = :targetCode AND
                base.id = base_currency_id AND target.id = target_currency_id
            RETURNING er.id as id, er.rate as rate,
                base.id as base_id, base.code as base_code,
                base.full_name as base_name, base.sign as base_sign,
                target.id as target_id, target.code as target_code,
                target.full_name as target_name, target.sign as target_sign
            """, rowMapperClass = ExchangeRateRowMapper.class)
    Optional<ExchangeRate> updateByPairCodes(String baseCode, String targetCode, BigDecimal rate);


    @Query(value = """
            SELECT er.id as id, er.rate as rate,
                   er.id as id, er.rate as rate,
                   base.id as base_id, base.code as base_code,
                   base.full_name as base_name, base.sign as base_sign,
                   target.id as target_id, target.code as target_code,
                   target.full_name as target_name, target.sign as target_sign
            FROM exchange_rates er
                     JOIN public.currencies base on base.id = er.base_currency_id
                     JOIN public.currencies target on target.id = er.target_currency_id
            WHERE
                base.code = :code1 AND target.code = :code2 OR
                base.code = :code2 AND target.code = :code1;
            """, rowMapperClass = ExchangeRateRowMapper.class)
    Optional<ExchangeRate> findByPairCodesBidirectional(String code1, String code2);
}
