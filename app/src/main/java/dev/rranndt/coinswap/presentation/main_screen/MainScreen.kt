package dev.rranndt.coinswap.presentation.main_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.rranndt.coinswap.R
import dev.rranndt.coinswap.presentation.main_screen.component.BottomSheetContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainScreenState,
    onEvent: (MainScreenEvent) -> Unit
) {
    val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0", "C")
    val context = LocalContext.current

    LaunchedEffect(key1 = state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var shouldBottomSheetShow by remember { mutableStateOf(false) }

    if (shouldBottomSheetShow) {
        ModalBottomSheet(
            sheetState = sheetState,
            dragHandle = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(
                        text = "Select Currency",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                }
            },
            onDismissRequest = { shouldBottomSheetShow = false }
        ) {
            BottomSheetContent(
                onItemClicked = { currentCode ->
                    onEvent(MainScreenEvent.BottomSheetItemClicked(currentCode))
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) shouldBottomSheetShow = false
                    }
                },
                currenciesList = state.currencyRates.values.toList()
            )
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "CoinSwap",
            fontFamily = FontFamily.Cursive,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Box(contentAlignment = Alignment.CenterStart) {
            Column {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        CurrencyRow(
                            currencyCode = state.fromCurrencyCode,
                            currencyName = state.currencyRates[state.fromCurrencyCode]?.name ?: "",
                            onDropDownIconClicked = {
                                shouldBottomSheetShow = true
                                onEvent(MainScreenEvent.FromCurrencySelect)
                            }
                        )
                        Text(
                            text = state.fromCurrencyValue,
                            fontSize = 40.sp,
                            color = if (state.selection == SelectionState.FROM) {
                                MaterialTheme.colorScheme.primary
                            } else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = { onEvent(MainScreenEvent.FromCurrencySelect) }
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = state.toCurrencyValue,
                            fontSize = 40.sp,
                            color = if (state.selection == SelectionState.TO) {
                                MaterialTheme.colorScheme.primary
                            } else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = { onEvent(MainScreenEvent.ToCurrencySelect) }
                            )
                        )
                        CurrencyRow(
                            currencyCode = state.toCurrencyCode,
                            currencyName = state.currencyRates[state.toCurrencyCode]?.name ?: "",
                            onDropDownIconClicked = {
                                shouldBottomSheetShow = true
                                onEvent(MainScreenEvent.ToCurrencySelect)
                            }
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(start = 40.dp)
                    .clip(CircleShape)
                    .clickable { onEvent(MainScreenEvent.SwapIconClicked) }
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sync),
                    contentDescription = "Swap Currency",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(25.dp)
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(horizontal = 35.dp)
        ) {
            items(keys) { key ->
                KeyboardButton(
                    key = key,
                    backgroundColor = if (key == "C") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    onClick = { onEvent(MainScreenEvent.NumberButtonClicked(key)) },
                    modifier = Modifier.aspectRatio(1f)
                )
            }
        }
    }
}

@Composable
fun CurrencyRow(
    currencyCode: String,
    currencyName: String,
    onDropDownIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = currencyCode,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = onDropDownIconClicked) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open Bottom Sheet"
            )
        }
        Spacer(modifier = modifier.weight(1f))
        Text(
            text = currencyName,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun KeyboardButton(
    key: String,
    backgroundColor: Color,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(8.dp)
            .clip(CircleShape)
            .background(color = backgroundColor)
            .clickable { onClick(key) }
    ) {
        Text(
            text = key,
            fontSize = 32.sp
        )
    }
}