package dev.rranndt.coinswap.domain.model

data class CurrencyRate(
    val name: String,

    val rate: Double,

    val code: String
)
