package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource

@Composable
fun createLayout() {
    val cards = setCards().permutate()
    val deck = remember { Deck(cards) }
    val finished by remember { deck.finished }
    setColumn {
        setRow {
            setButtons(4, 0, deck)
        }
        setRow {
            setButtons(4, 4, deck)
        }
        finishedScreen(finished)
    }
}


@Composable
fun setColumn (
    columnContent : @Composable (ColumnScope.() -> Unit)
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize()
            .verticalScroll(
                state = rememberScrollState(),
                overscrollEffect = rememberOverscrollEffect(),
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = columnContent,
    )
}


@Composable
fun setRow(
    rowContent : @Composable (RowScope.() -> Unit)
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .height(300.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        content = rowContent,
    )
}


@Composable
fun setButtons(
    number : Int,
    startIndex : Int,
    deck : Deck,
) {
    for (i in startIndex..<startIndex + number) {
        Button(
            onClick = { deck.onClick(i) },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(Color.Black),
        ) {
            Image(painterResource(deck.cards[i].face.value), null)
        }
    }
}

@Composable
fun finishedScreen(
    finished: Boolean
) {
    Row {
        AnimatedVisibility(
            visible = finished,
            enter = slideInVertically()
        ) {
            Text(
                text = "Victory!",
                fontSize = 90.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}