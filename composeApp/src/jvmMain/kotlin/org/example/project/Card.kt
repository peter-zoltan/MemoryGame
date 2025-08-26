package org.example.project

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import memorygame.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


class Card(
    val front : DrawableResource,
    val back : DrawableResource = Res.drawable.back,
    val blank : DrawableResource = Res.drawable.back,
) {

    var face = mutableStateOf(back)

    fun flip() {
        face.value = if (face.value == back) front else back
    }

    fun take() {
        face.value = blank
    }

    var clickable = true

    fun clickable() : Boolean {
        return clickable && (face.value == back)
    }

}


class Deck(val cards : List<Card>) {

    var revealedCounter = 0

    private val myScope = CoroutineScope(Dispatchers.Main)

    fun hideAll() {
        cards.forEach {
            if (it.face.value == it.front) it.flip()
        }
    }

    fun access(clickable : Boolean) {
        cards.forEach {
            it.clickable = clickable
        }
    }

    fun onClick(buttonIndex : Int) {
        if (!cards[buttonIndex].clickable()) {
            return
        }
        when (revealedCounter) {
            0, 1 -> {
                cards[buttonIndex].face.value = cards[buttonIndex].front
                revealedCounter++
            }
            else -> {
                //throw RuntimeException("No more than two cards may be revealed at once!")
            }
        }
        if (revealedCounter == 2) {
            myScope.launch {
                withContext(Dispatchers.IO) {
                    access(false)
                    delay(1000)
                    hideAll()
                    revealedCounter = 0
                    access(true)
                }
            }
        }
    }

}


fun setCards() : List<Card> {
    val cards = listOf(
        Card(Res.drawable.fool),
        Card(Res.drawable.hermit),
        Card(Res.drawable.hanged_man),
        Card(Res.drawable.justice),
        Card(Res.drawable.magician),
        Card(Res.drawable.moon),
        Card(Res.drawable.star),
        Card(Res.drawable.sun),
        Card(Res.drawable.world),
    )
    return cards
}


@Composable
fun createLayout() {
    val cards = setCards()
    val deck = remember { Deck(cards) }
    setColumn {
        setRow {
            setButtons(5, 0, deck)
        }
        setRow {
            setButtons(4, 5, deck)
        }
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