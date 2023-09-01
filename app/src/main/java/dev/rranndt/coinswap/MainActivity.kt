package dev.rranndt.coinswap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.rranndt.coinswap.presentation.main_screen.MainScreen
import dev.rranndt.coinswap.presentation.main_screen.MainScreenViewModel
import dev.rranndt.coinswap.presentation.theme.CoinSwapTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinSwapTheme {
                val viewModel: MainScreenViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                Surface {
                    MainScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}