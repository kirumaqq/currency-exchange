package io.naoki.currencyspring.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("currencies")
public record Currency(
        @Id
        Integer id,
        String code,
        String fullName,
        String sign
) {
}
