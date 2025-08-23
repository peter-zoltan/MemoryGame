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
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import memorygame.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


enum class Faces {
    Front, Back, Blank
}


class Card(
    front : DrawableResource,
    back : DrawableResource = Res.drawable.back,
    blank : DrawableResource = Res.drawable.back,
) {

    var content = Faces.Back

    var clickable = true

}


class VisibilityHandler(numberOfCards : Int) {

    val showContent = List(numberOfCards) { mutableStateOf(Faces.Back) }

    fun hideAll() {
        showContent.forEach {
            if (it.value == Faces.Front) it.value = Faces.Back
        }
    }

    var revealedCounter = 0

    private val myScope = CoroutineScope(Dispatchers.Main)

    fun onClick(buttonIndex : Int) {
        //if (!clickable) return
        if (showContent[buttonIndex].value != Faces.Back) {
            return
        }
        when (revealedCounter) {
            0, 1 -> {
                showContent[buttonIndex].value = Faces.Front
                revealedCounter++
            }
            else -> {
                //throw RuntimeException("No more than two cards may be revealed at once!")
            }
        }
        if (revealedCounter == 2) {
            myScope.launch {
                withContext(Dispatchers.IO) {
                    //forEach { clickable = false }
                    delay(1000)
                    hideAll()
                    revealedCounter = 0
                    //forEach { clickable = true }
                }
            }
        }
    }

}


fun setIcons() : List<DrawableResource> {
    val icons = listOf(
        Res.drawable.fool,
        Res.drawable.hermit,
        Res.drawable.hanged_man,
        Res.drawable.justice,
        Res.drawable.magician,
        Res.drawable.moon,
        Res.drawable.star,
        Res.drawable.sun,
        Res.drawable.world,
    )
    return icons
}


@Composable
fun createLayout() {
    val visibility = remember { VisibilityHandler(9) }
    val icons = setIcons()
    setColumn {
        setRow {
            setButtons(5, 0, visibility, icons)
        }
        setRow {
            setButtons(4, 5, visibility, icons)
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
    visibility : VisibilityHandler,
    icons : List<DrawableResource>,
) {
    for (i in startIndex..<startIndex + number) {
        Button(
            onClick = { visibility.onClick(i) },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(Color.Black),
        ) {
            when (visibility.showContent[i].value) {
                Faces.Front -> {
                    Image(painterResource(icons[i]), null)
                }
                Faces.Back -> {
                    Image(painterResource(Res.drawable.back), null)
                }
                Faces.Blank -> {
                    Image(painterResource(Res.drawable.back), null)
                }
            }
        }
    }
}