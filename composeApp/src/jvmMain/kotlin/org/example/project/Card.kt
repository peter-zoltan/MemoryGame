package org.example.project

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import memorygame.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource


class Card(
    val front : DrawableResource,
    val back : DrawableResource = Res.drawable.back,
    val blank : DrawableResource = Res.drawable.blank,
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

    fun checkMatch() {
        val first = cards.find { it.face.value == it.front }
        val second = cards.findLast { it.face.value == it.front }
        if (first!!.front == second!!.front) {
            first.take()
            second.take()
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
                    checkMatch()
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
        Card(Res.drawable.fool),
        Card(Res.drawable.hermit),
        Card(Res.drawable.hanged_man),
        Card(Res.drawable.justice),
        /*Card(Res.drawable.magician),
        Card(Res.drawable.moon),
        Card(Res.drawable.star),
        Card(Res.drawable.sun),
        Card(Res.drawable.world),*/
    )
    return cards
}

fun List<Card>.permutate() : List<Card> {
    val result = mutableListOf<Card>()
    val indexes = Array (this.size) { it }
    indexes.shuffle()
    for (i in 0..<this.size) {
        result.add(this[indexes[i]])
    }
    return result
}