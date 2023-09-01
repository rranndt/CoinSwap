package dev.rranndt.coinswap.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.rranndt.coinswap.data.local.CurrencyRateDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): CurrencyRateDatabase = Room
        .databaseBuilder(
            app,
            CurrencyRateDatabase::class.java,
            "currency.db"
        ).build()
}