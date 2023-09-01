package dev.rranndt.coinswap.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyRateEntity(
    val name: String,

    val rate: Double,

    @PrimaryKey
    val code: String
)
