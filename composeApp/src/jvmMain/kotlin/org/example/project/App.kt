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
import androidx.compose.ui.unit.IntOffset


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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            /*Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.fool), null)
                    Text("Compose: $greeting")
                }
            }*/
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
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
                    /*AnimatedVisibility(
                        visible = showContent[0].value,
                        enter = EnterTransition.None,
                        exit = ExitTransition.None,
                    ) {
                        Image(painterResource(Res.drawable.fool), null)
                    }
                    AnimatedVisibility(
                        visible = !showContent[0].value,
                        enter = EnterTransition.None,
                        exit = ExitTransition.None,
                    ) {
                        Image(painterResource(Res.drawable.back), null)
                    }*/
                }
            }
        }
    }
}