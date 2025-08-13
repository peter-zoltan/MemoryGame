package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.animation.*
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp


import memorygame.composeapp.generated.resources.Res
import memorygame.composeapp.generated.resources.*

@Composable
@Preview
fun App() {
    MaterialTheme {
        val showContent = remember { List(4) { mutableStateOf(false) } }
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
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .height(500.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { showContent[0].value = !showContent[0].value },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(Color.Black)
                ) {
                    if (showContent[0].value) {
                        Image(painterResource(Res.drawable.fool), null)
                    } else {
                        Image(painterResource(Res.drawable.back), null)
                    }
                }

                //Spacer(Modifier.width(16.dp))

            }

            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .height(500.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { showContent[1].value = !showContent[1].value },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(Color.Black)
                ) {
                    if (showContent[1].value) {
                        Image(painterResource(Res.drawable.hermit), null)
                    } else {
                        Image(painterResource(Res.drawable.back), null)
                    }
                }
            }
        }
    }
}