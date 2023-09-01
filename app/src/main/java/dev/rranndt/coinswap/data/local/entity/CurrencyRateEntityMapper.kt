package dev.rranndt.coinswap.data.local.entity

import dev.rranndt.coinswap.domain.model.CurrencyRate

fun CurrencyRateEntity.toCurrencyRate(): CurrencyRate {
    return CurrencyRate(
        name = name,
        rate = rate,
        code = code
    )
}

fun CurrencyRate.toCurrencyRateEntity(): CurrencyRateEntity {
    return CurrencyRateEntity(
        name = name,
        rate = rate,
        code = code
    )
}