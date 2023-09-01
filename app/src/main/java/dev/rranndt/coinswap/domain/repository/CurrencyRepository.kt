package dev.rranndt.coinswap.domain.repository

import dev.rranndt.coinswap.domain.model.CurrencyRate
import dev.rranndt.coinswap.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getCurrencyRatesList(): Flow<Resource<List<CurrencyRate>>>
}