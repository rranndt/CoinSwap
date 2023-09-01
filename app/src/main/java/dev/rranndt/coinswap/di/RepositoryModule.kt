package dev.rranndt.coinswap.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.rranndt.coinswap.data.local.CurrencyRateDatabase
import dev.rranndt.coinswap.data.remote.CurrencyApi
import dev.rranndt.coinswap.data.repository.CurrencyRepositoryImpl
import dev.rranndt.coinswap.domain.repository.CurrencyRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCurrencyRepository(
        api: CurrencyApi,
        db: CurrencyRateDatabase
    ): CurrencyRepository = CurrencyRepositoryImpl(api, db.currencyRateDao)
}