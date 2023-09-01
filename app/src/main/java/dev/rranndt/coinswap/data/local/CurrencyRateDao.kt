package dev.rranndt.coinswap.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.rranndt.coinswap.data.local.entity.CurrencyRateEntity

@Dao
interface CurrencyRateDao {
    @Upsert
    suspend fun upsertAll(currencyRates: List<CurrencyRateEntity>)

    @Query("SELECT * FROM currencyrateentity")
    suspend fun getAllCurrencyRates(): List<CurrencyRateEntity>
}