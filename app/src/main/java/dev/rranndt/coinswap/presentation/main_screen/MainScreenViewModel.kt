package dev.rranndt.coinswap.presentation.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rranndt.coinswap.domain.model.Resource
import dev.rranndt.coinswap.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()

    init {
        getCurrencyRatesList()
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {

            MainScreenEvent.FromCurrencySelect -> {
                _state.update { currentState ->
                    currentState.copy(selection = SelectionState.FROM)
                }
            }

            MainScreenEvent.ToCurrencySelect -> {
                _state.update { currentState ->
                    currentState.copy(selection = SelectionState.TO)
                }
            }

            MainScreenEvent.SwapIconClicked -> {
                _state.update { currentState ->
                    currentState.copy(
                        fromCurrencyCode = _state.value.toCurrencyCode,
                        fromCurrencyValue = _state.value.toCurrencyValue,
                        toCurrencyCode = _state.value.fromCurrencyCode,
                        toCurrencyValue = _state.value.fromCurrencyValue,
                    )
                }
            }

            is MainScreenEvent.BottomSheetItemClicked -> {
                if (_state.value.selection == SelectionState.FROM) {
                    _state.value = _state.value.copy(fromCurrencyCode = event.value)
                } else if (_state.value.selection == SelectionState.TO) {
                    _state.value = _state.value.copy(toCurrencyCode = event.value)
                }
                updateCurrencyValue("")
            }

            is MainScreenEvent.NumberButtonClicked -> {
                updateCurrencyValue(value = event.value)
            }
        }
    }

    private fun getCurrencyRatesList() {
        viewModelScope.launch {
            repository
                .getCurrencyRatesList()
                .collectLatest { result ->
                    _state.value = when (result) {
                        is Resource.Success -> {
                            _state.value.copy(
                                currencyRates = result.data?.associateBy { it.code }
                                    ?: emptyMap(),
                                error = null
                            )
                        }

                        is Resource.Error -> {
                            _state.value.copy(
                                currencyRates = result.data?.associateBy { it.code }
                                    ?: emptyMap(),
                                error = result.message
                            )
                        }
                    }
                }
        }
    }

    private fun updateCurrencyValue(value: String) {
        val currentCurrencyValue = when (_state.value.selection) {
            SelectionState.FROM -> _state.value.fromCurrencyValue
            SelectionState.TO -> _state.value.toCurrencyValue
        }
        val fromCurrencyRate =
            _state.value.currencyRates[_state.value.fromCurrencyCode]?.rate ?: 0.0
        val toCurrencyRate = _state.value.currencyRates[_state.value.toCurrencyCode]?.rate ?: 0.0

        val updatedCurrencyValue = when (value) {
            "C" -> "0.00"
            else -> if (currentCurrencyValue == "0.00") value else currentCurrencyValue + value
        }

        val numberFormat = DecimalFormat("#.00")

        when (_state.value.selection) {
            SelectionState.FROM -> {
                val fromValue = updatedCurrencyValue.toDoubleOrNull() ?: 0.0
                val toValue = fromValue / fromCurrencyRate * toCurrencyRate
                _state.update { currentState ->
                    currentState.copy(
                        fromCurrencyValue = updatedCurrencyValue,
                        toCurrencyValue = numberFormat.format(toValue)
                    )
                }
            }

            SelectionState.TO -> {
                val toValue = updatedCurrencyValue.toDoubleOrNull() ?: 0.0
                val fromValue = toValue / toCurrencyRate * fromCurrencyRate
                _state.update { currentState ->
                    currentState.copy(
                        toCurrencyValue = updatedCurrencyValue,
                        fromCurrencyValue = numberFormat.format(fromValue)
                    )
                }
            }
        }
    }
}