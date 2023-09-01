package dev.rranndt.coinswap.data.remote.dto

import dev.rranndt.coinswap.domain.model.CurrencyRate

fun CurrencyDto.toCurrencyRates(): List<CurrencyRate> {
    val currencyData = this.data
    return listOf(
        CurrencyRate(code = "INR", name = "Indian Rupee", rate = currencyData.INR),
        CurrencyRate(code = "EUR", name = "Euro", rate = currencyData.EUR),
        CurrencyRate(code = "USD", name = "US Dollar", rate = currencyData.USD),
        CurrencyRate(code = "JPY", name = "Japanese Yen", rate = currencyData.JPY),
        CurrencyRate(code = "BGN", name = "Bulgarian Lev", rate = currencyData.BGN),
        CurrencyRate(code = "CZK", name = "Czech Republic Koruna", rate = currencyData.CZK),
        CurrencyRate(code = "DKK", name = "Danish Krone", rate = currencyData.DKK),
        CurrencyRate(code = "GBP", name = "British Pound Sterling", rate = currencyData.GBP),
        CurrencyRate(code = "HUF", name = "Hungarian Forint", rate = currencyData.HUF),
        CurrencyRate(code = "PLN", name = "Polish Zloty", rate = currencyData.PLN),
        CurrencyRate(code = "RON", name = "Romanian Leu", rate = currencyData.RON),
        CurrencyRate(code = "SEK", name = "Swedish Krona", rate = currencyData.SEK),
        CurrencyRate(code = "CHF", name = "Swiss Franc", rate = currencyData.CHF),
        CurrencyRate(code = "ISK", name = "Icelandic Króna", rate = currencyData.ISK),
        CurrencyRate(code = "NOK", name = "Norwegian Krone", rate = currencyData.NOK),
        CurrencyRate(code = "HRK", name = "Croatian Kuna", rate = currencyData.HRK),
        CurrencyRate(code = "RUB", name = "Russian Ruble", rate = currencyData.RUB),
        CurrencyRate(code = "TRY", name = "Turkish Lira", rate = currencyData.TRY),
        CurrencyRate(code = "AUD", name = "Australian Dollar", rate = currencyData.AUD),
        CurrencyRate(code = "BRL", name = "Brazilian Real", rate = currencyData.BRL),
        CurrencyRate(code = "CAD", name = "Canadian Dollar", rate = currencyData.CAD),
        CurrencyRate(code = "CNY", name = "Chinese Yuan", rate = currencyData.CNY),
        CurrencyRate(code = "HKD", name = "Hong Kong Dollar", rate = currencyData.HKD),
        CurrencyRate(code = "IDR", name = "Indonesian Rupiah", rate = currencyData.IDR),
        CurrencyRate(code = "ILS", name = "Israeli New Sheqel", rate = currencyData.ILS),
        CurrencyRate(code = "KRW", name = "South Korean Won", rate = currencyData.KRW),
        CurrencyRate(code = "MXN", name = "Mexican Peso", rate = currencyData.MXN),
        CurrencyRate(code = "MYR", name = "Malaysian Ringgit", rate = currencyData.MYR),
        CurrencyRate(code = "NZD", name = "New Zealand Dollar", rate = currencyData.NZD),
        CurrencyRate(code = "PHP", name = "Philippine Peso", rate = currencyData.PHP),
        CurrencyRate(code = "SGD", name = "Singapore Dollar", rate = currencyData.SGD),
        CurrencyRate(code = "THB", name = "Thai Baht", rate = currencyData.THB),
        CurrencyRate(code = "ZAR", name = "South African Rand", rate = currencyData.ZAR)
    )
}