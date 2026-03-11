package ui.gui.processor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.gui.module.InstrumentModule
import java.awt.Toolkit

class ApplicationProcessor {
    private val instrumentModule = InstrumentModule()

    companion object {
        val PADDING_ROUND = 20.dp
    }

    fun showWindow() {
        val screenSize = Toolkit.getDefaultToolkit().screenSize

        application {
            Window(
                resizable = false,
                title = "Калькулятор уравнений",
                icon = painterResource("icon.png"),
                onCloseRequest = ::exitApplication,
                state = rememberWindowState(
                    width = (screenSize.width * .8).toInt().dp,
                    height = (screenSize.height * .6).toInt().dp
                )
            ) {
                MaterialTheme {
                    mainLayout()
                }
            }
        }
    }

    @Composable
    fun mainLayout() {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(PADDING_ROUND),
                contentAlignment = Alignment.Center
            ) {
                instrumentModule.content()
            }
            /*
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .fillMaxHeight()
                    .background(Color.LightGray)
            )
             */
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(PADDING_ROUND),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Правая панель",
                    color = Color.DarkGray
                )
            }
        }
    }
}
