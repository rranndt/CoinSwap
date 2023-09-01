package dev.rranndt.coinswap.data.repository

import dev.rranndt.coinswap.data.local.CurrencyRateDao
import dev.rranndt.coinswap.data.local.entity.toCurrencyRate
import dev.rranndt.coinswap.data.local.entity.toCurrencyRateEntity
import dev.rranndt.coinswap.data.remote.CurrencyApi
import dev.rranndt.coinswap.data.remote.dto.toCurrencyRates
import dev.rranndt.coinswap.domain.model.CurrencyRate
import dev.rranndt.coinswap.domain.model.Resource
import dev.rranndt.coinswap.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CurrencyRepositoryImpl(
    private val api: CurrencyApi,
    private val db: CurrencyRateDao
) : CurrencyRepository {
    override fun getCurrencyRatesList(): Flow<Resource<List<CurrencyRate>>> = flow {
        val localCurrencyRates = getLocalCurrencyRates()
        emit(Resource.Success(localCurrencyRates))

        try {
            val newRates = getRemoteCurrencyRates()
            updateLocalCurrencyRates(newRates)
            emit(Resource.Success(newRates))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = localCurrencyRates
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = "Oops, something wrong! ${e.message}",
                    data = localCurrencyRates
                )
            )
        }
    }

    private suspend fun getLocalCurrencyRates(): List<CurrencyRate> {
        return db.getAllCurrencyRates().map { it.toCurrencyRate() }
    }

    private suspend fun getRemoteCurrencyRates(): List<CurrencyRate> {
        val response = api.getLatestRates()
        return response.toCurrencyRates()
    }

    private suspend fun updateLocalCurrencyRates(currencyRates: List<CurrencyRate>) {
        db.upsertAll(currencyRates.map { it.toCurrencyRateEntity() })
    }
}